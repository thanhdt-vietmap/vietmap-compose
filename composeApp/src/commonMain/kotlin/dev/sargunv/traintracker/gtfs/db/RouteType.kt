package dev.sargunv.traintracker.gtfs.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.Serializable

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

    object Adapter : ColumnAdapter<RouteType, Long> {
        override fun decode(databaseValue: Long) = entries.first { it.value == databaseValue }
        override fun encode(value: RouteType) = value.value
    }

    class Serializer : LongColumnAdapterSerializer<RouteType>(Adapter)
}
