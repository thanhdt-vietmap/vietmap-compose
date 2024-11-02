package dev.sargunv.kotlincsv

import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.writeString
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

open class CsvFormat(private val config: Config = Config()) : StringFormat {
  override val serializersModule
    get() = config.serializersModule

  override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
    return decodeFromSource(deserializer, Buffer().apply { writeString(string) })
  }

  override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
    throw NotImplementedError("Encoding to CSV is not supported")
  }

  fun <T> decodeFromSource(deserializer: DeserializationStrategy<T>, source: Source): T {
    return deserializer.deserialize(CsvDecoder(source, config))
  }

  data class Config(
    val encoding: CsvEncoding = CsvEncoding(),
    val serializersModule: SerializersModule = EmptySerializersModule(),
    val namingStrategy: CsvNamingStrategy = CsvNamingStrategy.Identity,
    val treatMissingColumnsAsNull: Boolean = false,
    val ignoreUnknownKeys: Boolean = false,
  )

  object Csv : CsvFormat()

  object Tsv : CsvFormat(Config(CsvEncoding(delimiter = '\t')))
}
