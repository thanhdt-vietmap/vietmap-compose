package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.DatabaseDriverFactory

class GtfsScheduleDao(driverFactory: DatabaseDriverFactory, name: String) {
  private val db =
    GtfsScheduleDb(
      driverFactory.createDriver(GtfsScheduleDb.Schema, name),
      routeAdapter = Route.Adapter(routeTypeAdapter = RouteType.Adapter),
    )

  private val q = db.dbQueries

  fun getCachedETag(): String? {
    return q.getCacheVersion().executeAsOneOrNull()
  }

  fun update(newETag: String, newSchedule: GtfsSchedule) {
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

      newSchedule.agencies.forEach { q.insertAgency(it) }
      newSchedule.calendarDates.forEach { q.insertCalendarDate(it) }
      newSchedule.calendars.forEach { q.insertCalendar(it) }
      newSchedule.feedInfos.forEach { q.insertFeedInfo(it) }
      newSchedule.frequencies.forEach { q.insertFrequency(it) }
      newSchedule.routes.forEach { q.insertRoute(it) }
      newSchedule.shapes.forEach { q.insertShape(it) }
      newSchedule.stopTimes.forEach { q.insertStopTime(it) }
      newSchedule.stops.forEach { q.insertStop(it) }
      newSchedule.trips.forEach { q.insertTrip(it) }

      q.setCacheVersion(newETag)
    }
  }
}
