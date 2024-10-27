package dev.sargunv.traintracker.gtfs

import dev.sargunv.kmp.unzip.Unzipper
import dev.sargunv.traintracker.gtfs.db.GtfsCacheDb
import io.ktor.utils.io.core.readBytes
import kotlinx.io.Buffer
import kotlinx.io.RawSink
import kotlinx.io.buffered
import kotlinx.io.discardingSink

class GtfsSdk(
    private val gtfsClient: GtfsClient,
    private val gtfsCacheDb: GtfsCacheDb,
    private val unzipper: Unzipper,
) {
    suspend fun updateGtfsData(noCache: Boolean = false): Result<Unit> {
        val cachedETag = if (noCache) null else gtfsCacheDb.getCachedETag()
        return gtfsClient.getGtfsStaticArchive(cachedETag).map { maybeResponse ->
            val (eTag, feed) = maybeResponse ?: return@map
            unzipper.readArchive(
                source = feed,
                handleFile = { path ->
                    if (path != "agency.txt") {
                        println("#### Skipping file: $path")
                        discardingSink().buffered()
                    } else {
                        println("#### File: $path")
                        object : RawSink {
                            override fun close() {
                                println("#### File closed: $path")
                            }

                            override fun flush() {
                            }

                            override fun write(source: Buffer, byteCount: Long) {
                                print(source.readBytes(byteCount.toInt()).decodeToString())
                            }

                        }.buffered()
                    }
                },
                handleDirectory = { path ->
                    println("#### Directory: $path")
                },
            )
            gtfsCacheDb.update(eTag)
        }
    }
}