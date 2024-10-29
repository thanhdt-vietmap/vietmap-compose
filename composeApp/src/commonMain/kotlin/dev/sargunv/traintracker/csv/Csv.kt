package dev.sargunv.traintracker.csv

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

open class Csv(
    private val config: Config = Config(),
) : StringFormat {
    override val serializersModule get() = config.serializersModule

    override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
        return decodeFromSource(deserializer, Buffer().apply { writeString(string) })
    }

    override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
        throw NotImplementedError("Encoding to CSV is not supported")
    }

    fun <T> decodeFromSource(deserializer: DeserializationStrategy<T>, source: Source): T {
        return deserializer.deserialize(
            Decoder(
                CsvParser(source),
                config
            )
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    private class Decoder(
        csvParser: CsvParser,
        private val config: Config,
    ) : AbstractDecoder() {
        override val serializersModule get() = config.serializersModule

        private var level = 0

        // TODO: stream the CSV file instead of loading it all into memory
        private val records = csvParser.parseWithHeader()
            .map { row -> row.map { (key, value) -> key to value } }
            .toList()

        private var nullHeaders: List<String>? = null

        private var row = 0
        private var col = 0

        override fun beginStructure(descriptor: SerialDescriptor): Decoder {
            return when (level) {
                0 -> {
                    require(descriptor.kind == StructureKind.LIST) {
                        "Top-level structure must be a list (got ${descriptor.kind})"
                    }
                    level++
                    this
                }

                1 -> {
                    require(descriptor.kind == StructureKind.CLASS) {
                        "Second-level structure must be a class (got ${descriptor.kind})"
                    }

                    if (config.treatMissingColumnsAsNull) {
                        if (nullHeaders == null) {
                            val presentHeaders = records[row]
                                .map { config.namingStrategy.fromCsvName(it.first) }.toSet()
                            nullHeaders = descriptor.elementNames
                                .filterNot { presentHeaders.contains(it) }
                                .filter {
                                    !descriptor.isElementOptional(
                                        descriptor.getElementIndex(
                                            it
                                        )
                                    )
                                }
                        }
                    } else {
                        nullHeaders = emptyList()
                    }

                    level++
                    this
                }

                else -> throw NotImplementedError("Structures within fields are not supported")
            }
        }

        override fun endStructure(descriptor: SerialDescriptor) {
            level--
            if (level < 0) throw IllegalStateException("Unbalanced structure")
        }

        override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
            return when (level) {
                1 -> {
                    if (row >= records.size) return DECODE_DONE
                    return row
                }

                2 -> {
                    when {
                        // end of row
                        col >= records[row].size + nullHeaders!!.size -> {
                            row++
                            col = 0
                            return DECODE_DONE
                        }

                        // implicit null
                        col >= records[row].size -> {
                            val name = nullHeaders!![col - records[row].size]
                            return descriptor.getElementIndex(name)
                        }

                        // regular element
                        else -> {
                            val name = config.namingStrategy.fromCsvName(records[row][col].first)
                            descriptor.getElementIndex(name)
                        }
                    }
                }

                else -> throw NotImplementedError("Fields must be within a list of objects (got level $level)")
            }
        }

        override fun decodeValue(): Any {
            require(level == 2) { "Fields must be within a list of objects (got level $level)" }
            val ret = records[row][col].second
            col++
            return ret
        }

        override fun decodeString(): String = decodeValue() as String

        override fun decodeNotNullMark(): Boolean {
            if (col >= records[row].size) return false // implicit null
            return records[row][col].second != ""
        }

        override fun decodeNull(): Nothing? {
            if (col >= records[row].size) {
                // implicit null
                col++
                return null
            }
            val value = decodeString()
            if (value == "") return null
            throw IllegalStateException("Expected null, but got '$value'")
        }

        override fun decodeBoolean(): Boolean = decodeString().toBoolean()

        override fun decodeByte(): Byte = decodeString().toByte()

        override fun decodeShort(): Short = decodeString().toShort()

        override fun decodeInt(): Int = decodeString().toInt()

        override fun decodeLong(): Long = decodeString().toLong()

        override fun decodeFloat(): Float = decodeString().toFloat()

        override fun decodeDouble(): Double = decodeString().toDouble()

        override fun decodeChar(): Char {
            val str = decodeString()
            require(str.length == 1) { "Expected Char, but got '$str'" }
            return str[0]
        }

        override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
            val value = decodeString()
            val index = enumDescriptor.elementNames.indexOf(value)
            if (index >= 0) return index

            val ordinal = try {
                value.toInt()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Enum value '$value' not found in $enumDescriptor")
            }

            if (ordinal < 0 || ordinal >= enumDescriptor.elementsCount) {
                throw IllegalArgumentException("Enum ordinal $ordinal not found in $enumDescriptor")
            }

            return ordinal
        }
    }

    data class Config(
        val serializersModule: SerializersModule = EmptySerializersModule(),
        val namingStrategy: CsvNamingStrategy = CsvNamingStrategy.Identity,
        val treatMissingColumnsAsNull: Boolean = false,
    )

    data object Default : Csv()
}