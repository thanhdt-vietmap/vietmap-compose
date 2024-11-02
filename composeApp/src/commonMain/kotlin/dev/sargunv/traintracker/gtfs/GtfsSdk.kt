package dev.sargunv.traintracker.gtfs

class GtfsSdk(
  private val gtfsClient: GtfsClient,
  private val gtfsDao: GtfsDao,
  private val gtfsScheduleUnzipper: GtfsScheduleUnzipper,
) {
  suspend fun refreshSchedule(noCache: Boolean = false): Result<Unit> {
    val cachedETag = if (noCache) null else gtfsDao.getScheduleETag()

    return gtfsClient.getSchedule(cachedETag).map { maybeResponse ->
      val (eTag, scheduleZip) = maybeResponse ?: return@map
      println("Extracting GTFS archive ...")
      val schedule = gtfsScheduleUnzipper.unzip(scheduleZip)
      println("Updating cache ...")
      gtfsDao.updateSchedule(newETag = eTag, newSchedule = schedule)
      println("Cache updated to $eTag")
    }
  }
}
