package dev.sargunv.traintracker.gtfs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel

class GtfsScheduleClient(private val url: String) {
    private val client = HttpClient()

    suspend fun downloadGtfsIfModified(eTag: String?): ByteReadChannel? {
        val response = client.get(url) {
            if (eTag != null) headers["If-None-Match"] = eTag
        }
        return if (response.status.value == 304) {
            null
        } else {
            response.bodyAsChannel()
        }
    }
}