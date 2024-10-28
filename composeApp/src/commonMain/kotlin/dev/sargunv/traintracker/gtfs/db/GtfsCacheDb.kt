package dev.sargunv.traintracker.gtfs.db

import dev.sargunv.traintracker.DatabaseDriverFactory


class GtfsCacheDb(driverFactory: DatabaseDriverFactory) {
    private val db = GtfsCache(
        driverFactory.createDriver(GtfsCache.Schema, "gtfs_cache.db"),
        routeAdapter = Route.Adapter(
            routeTypeAdapter = RouteType.Adapter
        )
    )

    private val q = db.gtfsCacheQueries

    fun getCachedETag(): String? {
        return q.getCacheVersion().executeAsOneOrNull()
    }

    fun update(
        newETag: String,
        newRoutes: List<Route>? = null,
    ) {
        q.transactionWithResult {
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

            newRoutes?.forEach(q::insertRoute)

            q.setCacheVersion(newETag)
        }
    }
}