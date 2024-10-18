package dev.sargunv.traintracker.client

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel

const val AMTRAK_GTFS_URL = "https://content.amtrak.com/content/gtfs/GTFS.zip"
const val VIA_RAIL_GTFS_URL = "https://www.viarail.ca/sites/all/files/gtfs/viarail.zip"

class GtfsScheduleClient(private val url: String) {
    private val client = HttpClient()

    suspend fun getETag(): String {
        val response = client.head(url)
        return response.headers["ETag"] ?: throw Exception("ETag not found")
    }

    suspend fun downloadGtfs(): ByteReadChannel {
        val response = client.get(url)
        return response.bodyAsChannel()
    }
}