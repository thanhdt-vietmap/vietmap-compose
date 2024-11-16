package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.sargunv.maplibrekmp.compose.MaplibreMap
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.compose.layer.CircleLayer
import dev.sargunv.maplibrekmp.compose.layer.CirclePaint
import dev.sargunv.maplibrekmp.compose.layer.LineLayer
import dev.sargunv.maplibrekmp.compose.layer.LinePaint
import dev.sargunv.maplibrekmp.compose.rememberCameraState
import dev.sargunv.maplibrekmp.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrekmp.compose.uiSettings
import dev.sargunv.maplibrekmp.core.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.source.Shape
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.getColorScheme
import dev.sargunv.traintracker.getSheetHeight
import dev.sargunv.traintracker.max
import dev.sargunv.traintracker.plus
import org.koin.compose.KoinContext

@Composable
fun App() {
  KoinContext {
    MaterialTheme(colorScheme = getColorScheme()) {
      val safeDrawingInsets = WindowInsets.safeDrawing
      var text by remember { mutableStateOf("Hello, world!") }

      BottomSheetScaffold(
        sheetPeekHeight = max(128.dp, getSheetHeight() / 4),
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
          Column(
            modifier =
              Modifier
                // subtract handle height
                .height(getSheetHeight() - 48.dp)
                // getSheeetHeight already accounted for top inset
                .consumeWindowInsets(
                  WindowInsets(top = safeDrawingInsets.getTop(LocalDensity.current))
                )
                .verticalScroll(rememberScrollState())
          ) {
            Text(text = text)
          }
        },
      ) { sheetPadding ->
        val insetsPadding = safeDrawingInsets.asPaddingValues()

        val cameraState = rememberCameraState()
        val coroutineScope = rememberCoroutineScope()

        MaplibreMap(
          styleUrl = "https://tiles.openfreemap.org/styles/liberty",
          uiSettings =
            uiSettings(
              padding =
                remember(sheetPadding, insetsPadding) {
                  max(insetsPadding, sheetPadding + PaddingValues(8.dp))
                }
            ),
          cameraState = cameraState,
        ) {
          val routeSource =
            rememberGeoJsonSource(
              id = "amtrak-routes",
              shape = Shape.Url(Res.getUri("files/geojson/amtrak/routes.geojson")),
              options = GeoJsonOptions(tolerance = 0.001f),
            )
          val stationSource =
            rememberGeoJsonSource(
              id = "amtrak-stations",
              shape = Shape.Url(Res.getUri("files/geojson/amtrak/stations.geojson")),
            )

          Anchor.Below("boundary_3") {
            LineLayer(
              id = "routes-outline",
              source = routeSource,
              paint =
                LinePaint(
                  color = const(Color.White),
                  width = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
                ),
            )
            LineLayer(
              id = "routes-fill",
              source = routeSource,
              paint =
                LinePaint(
                  color = const(Color.Magenta),
                  width = interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
                ),
            )
          }

          Anchor.Top {
            CircleLayer(
              id = "stations-bus",
              source = stationSource,
              filter = const("BUS") eq get<String>(const("StnType"), properties<String>()),
              minZoom = 3f,
              paint =
                CirclePaint(
                  color = const(Color.White),
                  strokeColor = const(Color.Magenta),
                  radius =
                    interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
                  strokeWidth =
                    interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
                ),
              onClick = { features -> text = features.toString() },
            )
            CircleLayer(
              id = "stations-train",
              source = stationSource,
              filter = const("TRAIN") eq get<String>(const("StnType"), properties<String>()),
              minZoom = 2f,
              paint =
                CirclePaint(
                  color = const(Color.White),
                  strokeColor = const(Color.Magenta),
                  radius =
                    interpolate(exponential(const(2)), zoom(), 0 to const(4), 10 to const(8)),
                  strokeWidth =
                    interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
                ),
              onClick = { features -> text = features.toString() },
            )
          }
        }
      }
    }
  }
}
