package dev.sargunv.traintracker.gtfs

import dev.sargunv.kotlincsv.CsvFormat
import dev.sargunv.kotlincsv.CsvNamingStrategy
import dev.sargunv.kotlinzip.unzip
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

class GtfsScheduleUnzipper(private val ignoreFiles: Set<String> = emptySet()) {
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

  fun unzip(zipContent: Source, validate: Boolean = false): GtfsSchedule {
    val files = mutableMapOf<String, Buffer>()

    // TODO parse while streaming instead of buffering all the files up at once
    unzip(
      zipArchive = zipContent,
      handleFile = { path, content ->
        if (path in fileHandlers && path !in ignoreFiles) {
          println("Extracting $path")
          if (path in files) println("Warning: Duplicate file $path")
          files[path] = Buffer().also { content.transferTo(it) }
        } else {
          println("Skipping $path")
        }
      },
    )

    println("Parsing GTFS files ...")

    val builder = GtfsSchedule.Builder()
    files.forEach { (path, buffer) ->
      val numRecords = fileHandlers.getValue(path).handle(builder, buffer)
      println("Parsed $numRecords records from $path")
    }

    return builder.build(validate = validate)
  }

  private interface FileHandler {
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
      CsvFormat(
        Config(
          namingStrategy = CsvNamingStrategy.SnakeCase,
          treatMissingColumnsAsNull = true,
          ignoreUnknownKeys = true,
        )
      )
  }
}
