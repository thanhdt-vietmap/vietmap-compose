package dev.sargunv.traintracker.gtfs.db

import dev.sargunv.traintracker.DatabaseDriverFactory

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

            q.insertShape(
                Shape(
                    shapeId = "1",
                    shapePtLat = 0.0,
                    shapePtLon = 0.0,
                    shapePtSequence = 0,
                    shapeDistTraveled = 0.0
                )
            )
            q.insertShape(
                Shape(
                    shapeId = "1",
                    shapePtLat = 10.0,
                    shapePtLon = 0.0,
                    shapePtSequence = 1,
                    shapeDistTraveled = 0.0
                )
            )
            q.insertShape(
                Shape(
                    shapeId = "1",
                    shapePtLat = 10.0,
                    shapePtLon = 10.0,
                    shapePtSequence = 2,
                    shapeDistTraveled = 0.0
                )
            )
            q.insertShape(
                Shape(
                    shapeId = "1",
                    shapePtLat = 0.0,
                    shapePtLon = 10.0,
                    shapePtSequence = 3,
                    shapeDistTraveled = 0.0
                )
            )
            q.insertShape(
                Shape(
                    shapeId = "1",
                    shapePtLat = 0.0,
                    shapePtLon = 0.0,
                    shapePtSequence = 4,
                    shapeDistTraveled = 0.0
                )
            )

            q.selectAllShapes()
                .executeAsList()
                .groupBy { it.shapeId }
        }
    }
}