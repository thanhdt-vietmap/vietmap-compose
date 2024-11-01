package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.csv.Csv
import dev.sargunv.traintracker.csv.CsvNamingStrategy
import dev.sargunv.traintracker.zip.Unzipper
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.discardingSink
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

private val fileHandlers =
  mapOf(
    "routes.txt" to CsvHandler(RouteSerializer, GtfsSchedule.Builder::addRoute),
    "trips.txt" to CsvHandler(TripSerializer, GtfsSchedule.Builder::addTrip),
    "stops.txt" to CsvHandler(StopSerializer, GtfsSchedule.Builder::addStop),
    "stop_times.txt" to CsvHandler(StopTimeSerializer, GtfsSchedule.Builder::addStopTime),
    "calendar.txt" to CsvHandler(CalendarSerializer, GtfsSchedule.Builder::addCalendar),
    "calendar_dates.txt" to
      CsvHandler(CalendarDateSerializer, GtfsSchedule.Builder::addCalendarDate),
    "agency.txt" to CsvHandler(AgencySerializer, GtfsSchedule.Builder::addAgency),
    "feed_info.txt" to CsvHandler(FeedInfoSerializer, GtfsSchedule.Builder::addFeedInfo),
    "frequencies.txt" to CsvHandler(FrequencySerializer, GtfsSchedule.Builder::addFrequency),
    "shapes.txt" to CsvHandler(ShapeSerializer, GtfsSchedule.Builder::addShape),
  )

class GtfsSdk(
  private val gtfsClient: GtfsClient,
  private val gtfsScheduleDao: GtfsScheduleDao,
  private val unzipper: Unzipper,
) {

  suspend fun refreshSchedule(noCache: Boolean = false): Result<Unit> {
    val cachedETag = if (noCache) null else gtfsScheduleDao.getCachedETag()

    return gtfsClient.getSchedule(cachedETag).map { maybeResponse ->
      val (eTag, scheduleZip) = maybeResponse ?: return@map

      val files =
        mutableMapOf<String, Buffer>().ifEmpty { fileHandlers.keys.associateWith { Buffer() } }

      println("Extracting GTFS archive ...")

      // TODO parse while streaming instead of buffering all the files up at once
      unzipper.readArchive(
        source = scheduleZip,
        handleFile = { path ->
          if (path in fileHandlers) {
            println("Extracting $path")
            files.getValue(path)
          } else {
            println("Skipping $path")
            discardingSink().buffered()
          }
        },
      )

      println("Parsing GTFS files ...")

      val builder = GtfsSchedule.Builder()
      files.forEach { (path, buffer) ->
        val numRecords = fileHandlers.getValue(path).handle(builder, buffer)
        println("Parsed $numRecords records from $path")
      }

      println("Updating cache ...")

      gtfsScheduleDao.update(newETag = eTag, newSchedule = builder.build())

      println("Cache updated to $eTag")
    }
  }
}

interface FileHandler {
  fun handle(builder: GtfsSchedule.Builder, source: Source): Int
}

private class CsvHandler<T>(
  val rowSerializer: KSerializer<T>,
  val addRecord: GtfsSchedule.Builder.(T) -> Unit,
) : FileHandler {
  override fun handle(builder: GtfsSchedule.Builder, source: Source) =
    GtfsCsv.decodeFromSource(ListSerializer(rowSerializer), source)
      .map { builder.addRecord(it) }
      .count()

  object GtfsCsv :
    Csv(
      Config(
        namingStrategy = CsvNamingStrategy.SnakeCase,
        treatMissingColumnsAsNull = true,
        ignoreUnknownKeys = true,
      )
    )
}
