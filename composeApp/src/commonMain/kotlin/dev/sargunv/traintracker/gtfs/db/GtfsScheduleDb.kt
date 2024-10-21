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

    suspend fun clearAndInsert() {
        db.transactionWithResult {
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
        }
    }
}