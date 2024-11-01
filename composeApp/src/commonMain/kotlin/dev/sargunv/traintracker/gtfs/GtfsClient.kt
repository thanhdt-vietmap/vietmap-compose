package dev.sargunv.traintracker.gtfs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import io.ktor.utils.io.core.writeFully
import kotlinx.io.Buffer
import kotlinx.io.Source

class GtfsClient(private val scheduleUrl: String) {
  private val client = HttpClient()

  private suspend fun makeGetScheduleRequest(localETag: String?) = runCatching {
    client.get(scheduleUrl) { if (localETag != null) headers["If-None-Match"] = localETag }
  }

  suspend fun getSchedule(localETag: String?) =
    makeGetScheduleRequest(localETag).map {
      when (it.status.value) {
        200 ->
          GetScheduleResponse(
            eTag = it.headers["ETag"]!!,
            scheduleZip = Buffer().apply { writeFully(it.bodyAsBytes()) }, // TODO stream this
          )

        304 -> null
        else -> throw Exception("Unexpected HTTP status code ${it.status.value}")
      }
    }

  data class GetScheduleResponse(val eTag: String, val scheduleZip: Source)
}
