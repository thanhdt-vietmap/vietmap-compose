package dev.sargunv.kotlincsv

import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.readString
import kotlinx.io.writeString
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.StringFormat
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

public open class CsvFormat(private val config: Config = Config()) : StringFormat {
  override val serializersModule: SerializersModule = config.serializersModule

  override fun <T> decodeFromString(deserializer: DeserializationStrategy<T>, string: String): T {
    return decodeFromSource(deserializer, Buffer().apply { writeString(string) })
  }

  override fun <T> encodeToString(serializer: SerializationStrategy<T>, value: T): String {
    return Buffer().also { encodeToSink(serializer, value, it) }.readString()
  }

  public fun <T> decodeFromSource(deserializer: DeserializationStrategy<T>, source: Source): T {
    return deserializer.deserialize(CsvDecoder(source, config))
  }

  public fun <T> encodeToSink(serializer: SerializationStrategy<T>, value: T, sink: Sink) {
    serializer.serialize(CsvEncoder(sink, config), value)
  }

  public data class Config(
    val encoding: CsvEncoding = CsvEncoding(),
    val serializersModule: SerializersModule = EmptySerializersModule(),
    val namingStrategy: CsvNamingStrategy = CsvNamingStrategy.Identity,
    val treatMissingColumnsAsNull: Boolean = false,
    val ignoreUnknownKeys: Boolean = false,
    val writeEnumsByName: Boolean = true,
  )

  public object Csv : CsvFormat()

  public object Tsv : CsvFormat(Config(CsvEncoding(delimiter = '\t')))
}
