package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.gtfs.db.GtfsCacheDb

class GtfsSdk(
    private val gtfsClient: GtfsClient,
    private val gtfsCacheDb: GtfsCacheDb,
) {
    suspend fun updateGtfsData(): Result<Unit> =
        gtfsClient.getGtfsStaticArchive(gtfsCacheDb.getCachedETag()).map { response ->
            response?.let {
                println("Updating GTFS data; new ETag: ${it.eTag}")
                gtfsCacheDb.update(it.eTag)
            } ?: println("GTFS data is up to date")
        }
}