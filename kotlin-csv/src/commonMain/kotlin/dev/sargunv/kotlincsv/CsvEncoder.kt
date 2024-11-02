package dev.sargunv.kotlincsv

import kotlinx.io.Sink
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.elementDescriptors
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder

@OptIn(ExperimentalSerializationApi::class)
internal class CsvEncoder(private val sink: Sink, private val config: CsvFormat.Config) :
  AbstractEncoder() {

  private val csvWriter = CsvWriter(sink, config.encoding)
  private var nextIndex: Int = -1
  private var level = 0
  private lateinit var header: List<String>
  private lateinit var record: MutableList<String>
  private lateinit var recordDescriptor: SerialDescriptor

  override val serializersModule = config.serializersModule

  override fun beginCollection(
    descriptor: SerialDescriptor,
    collectionSize: Int,
  ): CompositeEncoder {
    require(level == 0) { "Top-level structure must be a list of records" }

    require(!::recordDescriptor.isInitialized) { "beginCollection called twice" }
    recordDescriptor = descriptor.elementDescriptors.first()
    header = recordDescriptor.elementNames.toList()
    csvWriter.writeRecord(header.map { config.namingStrategy.toCsvName(it) })
    record = MutableList(header.size) { "" }

    level++
    return this
  }

  override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
    require(level == 1) { "Top-level structure must be a list of records" }
    level++
    require(descriptor === recordDescriptor) {
      "All records must have the same structure (expected $recordDescriptor, got $descriptor)"
    }
    return this
  }

  override fun endStructure(descriptor: SerialDescriptor) {
    level--
    require(level >= 0) { "Unbalanced structure" }
    when (level) {
      1 -> {
        csvWriter.writeRecord(record)
        record.fill("")
      }
      0 -> sink.close()
    }
  }

  override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
    return when (level) {
      1 -> true
      2 -> {
        nextIndex = index
        true
      }
      else -> throw IllegalStateException("Invalid level $level")
    }
  }

  override fun encodeString(value: String) {
    record[nextIndex] = value
    nextIndex = -1
  }

  override fun encodeValue(value: Any) {
    encodeString(value.toString())
  }

  override fun encodeNull() {
    encodeString("")
  }

  override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
    if (config.writeEnumsByName) encodeString(enumDescriptor.getElementName(index))
    else encodeInt(index)
  }
}
