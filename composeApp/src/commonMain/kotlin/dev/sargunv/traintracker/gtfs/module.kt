package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.VIA_RAIL
import dev.sargunv.traintracker.gtfs.db.GtfsCacheDb
import org.koin.dsl.module

val gtfsModule = module {
    single { GtfsCacheDb(driverFactory = get()) }
    single { GtfsClient(staticFeedUrl = VIA_RAIL) }
    single { GtfsSdk(gtfsClient = get(), gtfsCacheDb = get()) }
}