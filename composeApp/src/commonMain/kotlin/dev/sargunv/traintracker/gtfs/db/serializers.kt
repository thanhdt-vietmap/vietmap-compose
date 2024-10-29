package dev.sargunv.traintracker.gtfs.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = Agency::class) object AgencySerializer

@Serializer(forClass = CacheVersion::class) object CacheVersionSerializer

@Serializer(forClass = Calendar::class) object CalendarSerializer

@Serializer(forClass = CalendarDate::class) object CalendarDateSerializer

@Serializer(forClass = FeedInfo::class) object FeedInfoSerializer

@Serializer(forClass = Frequency::class) object FrequencySerializer

@Serializer(forClass = Route::class) object RouteSerializer

@Serializer(forClass = Shape::class) object ShapeSerializer

@Serializer(forClass = Stop::class) object StopSerializer

@Serializer(forClass = StopTime::class) object StopTimeSerializer

@Serializer(forClass = Trip::class) object TripSerializer

abstract class SerializerByColumnAdapter<T : Any, S>(
    private val adapter: ColumnAdapter<T, S>,
) : KSerializer<T> {
  override fun deserialize(decoder: Decoder): T {
    return adapter.decode(decode(decoder))
  }

  override fun serialize(encoder: Encoder, value: T) {
    encode(encoder, adapter.encode(value))
  }

  abstract fun decode(decoder: Decoder): S

  abstract fun encode(encoder: Encoder, value: S)
}

open class SerializerByLongColumnAdapter<T : Any>(
    adapter: ColumnAdapter<T, Long>,
) : SerializerByColumnAdapter<T, Long>(adapter) {
  override val descriptor =
      PrimitiveSerialDescriptor(adapter::class.qualifiedName!!, PrimitiveKind.LONG)

  override fun decode(decoder: Decoder) = decoder.decodeLong()

  override fun encode(encoder: Encoder, value: Long) = encoder.encodeLong(value)
}
