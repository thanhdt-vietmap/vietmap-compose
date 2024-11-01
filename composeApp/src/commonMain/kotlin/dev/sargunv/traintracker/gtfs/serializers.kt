@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.traintracker.gtfs

import app.cash.sqldelight.ColumnAdapter
import kotlin.enums.EnumEntries
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
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

@Serializable(with = RouteType.Serializer::class)
enum class RouteType(val value: Long) {
  TRAM(0), // or streetcar, light rail
  SUBWAY(1), // or metro
  RAIL(2),
  BUS(3),
  FERRY(4),
  CABLE_TRAM(5), // e.g. a cable car in San Francisco
  AERIAL_LIFT(6), // e.g. a gondola lift
  FUNICULAR(7),
  TROLLEYBUS(11),
  MONORAIL(12);

  object Adapter : EnumAsLongAdapter<RouteType>(entries)

  class Serializer : EnumAsLongSerializer<RouteType>(Adapter)
}

open class EnumAsLongAdapter<T : Enum<T>>(private val entries: EnumEntries<T>) :
  ColumnAdapter<T, Long> {
  override fun decode(databaseValue: Long) = entries.first { it.ordinal.toLong() == databaseValue }

  override fun encode(value: T) = value.ordinal.toLong()
}

open class EnumAsLongSerializer<T : Enum<T>>(private val adapter: ColumnAdapter<T, Long>) :
  KSerializer<T> {
  override val descriptor =
    PrimitiveSerialDescriptor(adapter::class.qualifiedName!!, PrimitiveKind.LONG)

  override fun deserialize(decoder: Decoder) = adapter.decode(decoder.decodeLong())

  override fun serialize(encoder: Encoder, value: T) = encoder.encodeLong(adapter.encode(value))
}
