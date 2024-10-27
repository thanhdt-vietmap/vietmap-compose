package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.gtfs.db.GtfsCacheDb
import org.koin.dsl.module

const val VIA_RAIL = "https://www.viarail.ca/sites/all/files/gtfs/viarail.zip"

val gtfsModule = module {
    single { GtfsCacheDb(driverFactory = get()) }
    single { GtfsClient(staticFeedUrl = VIA_RAIL) }
    single { GtfsSdk(gtfsClient = get(), gtfsCacheDb = get(), unzipper = get()) }
}