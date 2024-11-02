package dev.sargunv.traintracker.gtfs

data class GtfsSchedule(
  val agencies: List<Agency>,
  val stops: List<Stop>,
  val routes: List<Route>,
  val trips: List<Trip>,
  val stopTimes: List<StopTime>,
  val calendars: List<Calendar>,
  val calendarDates: List<CalendarDate>,
  val shapes: List<Shape>,
  val frequencies: List<Frequency>,
  val feedInfos: List<FeedInfo>,
) {
  class Builder {
    private val agencies = mutableListOf<Agency>()
    private val stops = mutableListOf<Stop>()
    private val routes = mutableListOf<Route>()
    private val trips = mutableListOf<Trip>()
    private val stopTimes = mutableListOf<StopTime>()
    private var calendars = mutableListOf<Calendar>()
    private var calendarDates = mutableListOf<CalendarDate>()
    private var shapes = mutableListOf<Shape>()
    private var frequencies = mutableListOf<Frequency>()
    private var feedInfos = mutableListOf<FeedInfo>()

    fun addAgency(agency: Agency) = apply { agencies.add(agency) }

    fun addStop(stop: Stop) = apply { stops.add(stop) }

    fun addRoute(route: Route) = apply { routes.add(route) }

    fun addTrip(trip: Trip) = apply { trips.add(trip) }

    fun addStopTime(stopTime: StopTime) = apply { stopTimes.add(stopTime) }

    fun addCalendar(calendar: Calendar) = apply { calendars.add(calendar) }

    fun addCalendarDate(calendarDate: CalendarDate) = apply { calendarDates.add(calendarDate) }

    fun addShape(shape: Shape) = apply { shapes.add(shape) }

    fun addFrequency(frequency: Frequency) = apply { frequencies.add(frequency) }

    fun addFeedInfo(feedInfo: FeedInfo) = apply { feedInfos.add(feedInfo) }

    fun build(validate: Boolean = true): GtfsSchedule {
      if (validate) {
        require(agencies.isNotEmpty()) { "Agencies must be provided" }
        require(stops.isNotEmpty()) { "Stops must be provided" } // TODO unless Locations provided
        require(routes.isNotEmpty()) { "Routes must be provided" }
        require(trips.isNotEmpty()) { "Trips must be provided" }
        require(stopTimes.isNotEmpty()) { "StopTimes must be provided" }
        require(calendars.isNotEmpty() || calendarDates.isNotEmpty()) {
          "Either Calendars or CalendarDates must be provided"
        }
        // TODO require FeedInfos if Translations provided
      }

      return GtfsSchedule(
        agencies = agencies,
        stops = stops,
        routes = routes,
        trips = trips,
        stopTimes = stopTimes,
        calendars = calendars,
        calendarDates = calendarDates,
        shapes = shapes,
        frequencies = frequencies,
        feedInfos = feedInfos,
      )
    }
  }
}
