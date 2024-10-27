package dev.sargunv.traintracker.gtfs

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.cancel
import io.ktor.utils.io.core.writeFully
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.runBlocking
import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.readByteArray

fun ByteReadChannel.toSource() = object : RawSource {
    override fun close() = cancel()

    override fun readAtMostTo(sink: Buffer, byteCount: Long) = runBlocking {
        val bytes = readRemaining(byteCount).readByteArray()
        sink.writeFully(bytes)
        bytes.size.toLong()
    }
}.buffered()

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
                    feed = it.bodyAsChannel().toSource()
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