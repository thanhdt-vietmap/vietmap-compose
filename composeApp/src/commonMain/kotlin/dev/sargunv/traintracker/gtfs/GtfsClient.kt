package dev.sargunv.traintracker.gtfs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel


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
                    feed = it.bodyAsChannel()
                )

                304 -> null
                else -> throw Exception("HTTP status code ${it.status.value}")
            }
        }

    class StaticArchiveResponse(
        val eTag: String,
        val feed: ByteReadChannel
    )
}