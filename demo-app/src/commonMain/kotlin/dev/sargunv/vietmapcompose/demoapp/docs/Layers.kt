@file:Suppress("unused")

package dev.sargunv.vietmapcompose.demoapp.docs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.sargunv.vietmapcompose.compose.ClickResult
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.compose.layer.Anchor
import dev.sargunv.vietmapcompose.compose.layer.CircleLayer
import dev.sargunv.vietmapcompose.compose.layer.LineLayer
import dev.sargunv.vietmapcompose.compose.source.getBaseSource
import dev.sargunv.vietmapcompose.compose.source.rememberGeoJsonSource
import dev.sargunv.vietmapcompose.demoapp.generated.Res
import dev.sargunv.vietmapcompose.expressions.dsl.const
import dev.sargunv.vietmapcompose.expressions.dsl.exponential
import dev.sargunv.vietmapcompose.expressions.dsl.interpolate
import dev.sargunv.vietmapcompose.expressions.dsl.zoom
import dev.sargunv.vietmapcompose.expressions.value.LineCap
import dev.sargunv.vietmapcompose.expressions.value.LineJoin
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Layers() {
  // -8<- [start:simple]
  MaplibreMap(styleUri = "https://tiles.openfreemap.org/styles/liberty") {
    val tiles = getBaseSource(id = "openmaptiles")
    CircleLayer(id = "example", source = tiles, sourceLayer = "poi")
  }
  // -8<- [end:simple]

  MaplibreMap {
    val amtrakStations =
      rememberGeoJsonSource(
        id = "amtrak-stations",
        uri = Res.getUri("files/data/amtrak_stations.geojson"),
      )

    // -8<- [start:amtrak-1]
    val amtrakRoutes =
      rememberGeoJsonSource(
        id = "amtrak-routes",
        uri = Res.getUri("files/data/amtrak_routes.geojson"),
      )
    LineLayer(
      id = "amtrak-routes-casing",
      source = amtrakRoutes,
      color = const(Color.White),
      width = const(6.dp),
    )
    LineLayer(
      id = "amtrak-routes",
      source = amtrakRoutes,
      color = const(Color.Blue),
      width = const(4.dp),
    )
    // -8<- [end:amtrak-1]

    // -8<- [start:amtrak-2]
    LineLayer(
      id = "amtrak-routes",
      source = amtrakRoutes,
      cap = const(LineCap.Round),
      join = const(LineJoin.Round),
      color = const(Color.Blue),
      width =
        interpolate(
          type = exponential(1.2f),
          input = zoom(),
          5 to const(0.4.dp),
          6 to const(0.7.dp),
          7 to const(1.75.dp),
          20 to const(22.dp),
        ),
    )
    // -8<- [end:amtrak-2]

    // -8<- [start:anchors]
    Anchor.Above("road_motorway") { LineLayer(id = "amtrak-routes", source = amtrakRoutes) }
    // -8<- [end:anchors]

    // -8<- [start:interaction]
    CircleLayer(
      id = "amtrak-stations",
      source = amtrakStations,
      onClick = { features ->
        println("Clicked on ${features[0].json()}")
        ClickResult.Consume
      },
    )
    // -8<- [end:interaction]
  }
}
