package dev.sargunv.traintracker.gtfs

import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class GtfsAgency(val scheduleUrl: String) {
  ViaRail(scheduleUrl = "https://www.viarail.ca/sites/all/files/gtfs/viarail.zip"),
  Amtrak(scheduleUrl = "https://content.amtrak.com/content/gtfs/GTFS.zip"),
}

val gtfsModule = module {
  for (agency in GtfsAgency.entries) {
    single(named(agency)) {
      GtfsSdk(
        gtfsClient = GtfsClient(scheduleUrl = agency.scheduleUrl),
        gtfsDao = GtfsDao(driverFactory = get(), name = "${agency.name}.db"),
        gtfsScheduleUnzipper = GtfsScheduleUnzipper(unzipper = get()),
      )
    }
  }
}
