package dev.sargunv.traintracker.gtfs.db

import dev.sargunv.traintracker.DatabaseDriverFactory

fun parseCsv(): List<Shape> {
    return shapesCsv.split("\n").map {
        val parts = it.split(",")
        Shape(
            shapeId = parts[0],
            shapePtLat = parts[1].toDouble(),
            shapePtLon = parts[2].toDouble(),
            shapePtSequence = parts[3].toLong(),
            shapeDistTraveled = parts[4].toDouble()
        )
    }
}

class GtfsScheduleDb(driverFactory: DatabaseDriverFactory) {
    private val db = GtfsSchedule(
        driverFactory.createDriver(GtfsSchedule.Schema, "gtfs_schedule.db"),
        routeAdapter = Route.Adapter(
            routeTypeAdapter = RouteType.Adapter
        )
    )

    private val q = db.gtfsScheduleQueries

    suspend fun clearAndInsert(): Map<String, List<Shape>> {
        return db.transactionWithResult {
            // clear the db
            q.deleteAllAgencies()
            q.deleteAllCalendarDates()
            q.deleteAllCalendars()
            q.deleteAllFeedInfos()
            q.deleteAllFrequencies()
            q.deleteAllRoutes()
            q.deleteAllShapes()
            q.deleteAllStopTimes()
            q.deleteAllStops()
            q.deleteAllTrips()

            parseCsv().forEach {
                q.insertShape(it)
            }

            q.selectAllShapes()
                .executeAsList()
                .groupBy { it.shapeId }
        }
    }
}