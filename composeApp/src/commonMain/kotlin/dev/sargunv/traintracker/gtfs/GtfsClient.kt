package dev.sargunv.traintracker.gtfs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import io.ktor.utils.io.core.writeFully
import kotlinx.io.Buffer
import kotlinx.io.Source

class GtfsClient(private val staticFeedUrl: String) {
    private val client = HttpClient()

    suspend fun getGtfsStaticArchive(localETag: String?) =
        runCatching {
            client.get(staticFeedUrl) {
                if (localETag != null) headers["If-None-Match"] = localETag
            }
        }.map {
            when (it.status.value) {
                200 -> StaticArchiveResponse(
                    eTag = it.headers["ETag"]!!,
                    feed = Buffer().apply { writeFully(it.bodyAsBytes()) } // TODO stream this
                )

                304 -> null
                else -> throw Exception("Unexpected HTTP status code ${it.status.value}")
            }
        }

    data class StaticArchiveResponse(
        val eTag: String,
        val feed: Source
    )
}