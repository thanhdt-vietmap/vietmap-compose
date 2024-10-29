@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.traintracker.gtfs

import dev.sargunv.traintracker.csv.Csv
import dev.sargunv.traintracker.csv.CsvNamingStrategy
import dev.sargunv.traintracker.gtfs.db.GtfsCacheDb
import dev.sargunv.traintracker.gtfs.db.Route
import dev.sargunv.traintracker.gtfs.db.RouteSerializer
import dev.sargunv.traintracker.zip.Unzipper
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.discardingSink
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer

class GtfsSdk(
    private val gtfsClient: GtfsClient,
    private val gtfsCacheDb: GtfsCacheDb,
    private val unzipper: Unzipper,
) {
  private val csv = Csv(Csv.Config(namingStrategy = CsvNamingStrategy.SnakeCase))

  suspend fun updateGtfsData(noCache: Boolean = false) =
      gtfsClient.getGtfsStaticArchive(if (noCache) null else gtfsCacheDb.getCachedETag()).map {
          maybeResponse ->
        val (eTag, feed) = maybeResponse ?: return@map

        val routesBuf = Buffer()

        unzipper.readArchive(
            source = feed,
            handleFile = { path ->
              if (path != "routes.txt") {
                discardingSink().buffered()
              } else {
                routesBuf
              }
            },
        )

        val routes: List<Route> = csv.decodeFromSource(ListSerializer(RouteSerializer), routesBuf)
        println(routes)

        gtfsCacheDb.update(newETag = eTag, newRoutes = routes)

        println("#### Cache updated to $eTag")
      }
}
