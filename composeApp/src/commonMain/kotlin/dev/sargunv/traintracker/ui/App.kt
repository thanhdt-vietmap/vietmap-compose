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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.sargunv.maplibrekmp.compose.MaplibreMap
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.compose.layer.LineLayer
import dev.sargunv.maplibrekmp.compose.rememberCameraState
import dev.sargunv.maplibrekmp.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrekmp.compose.uiSettings
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.core.data.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.data.ShapeOptions
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.getColorScheme
import dev.sargunv.traintracker.getSheetHeight
import dev.sargunv.traintracker.max
import dev.sargunv.traintracker.plus
import io.github.dellisd.spatialk.geojson.Position
import org.koin.compose.KoinContext

@Composable
fun App() {
  KoinContext {
    MaterialTheme(colorScheme = getColorScheme()) {
      val safeDrawingInsets = WindowInsets.safeDrawing

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
          ) {}
        },
      ) { sheetPadding ->
        val insetsPadding = safeDrawingInsets.asPaddingValues()

        val mapPadding =
          remember(sheetPadding, insetsPadding) {
            max(insetsPadding, sheetPadding + PaddingValues(8.dp))
          }

        val cameraState =
          rememberCameraState(
            CameraPosition(
              // Chicago Union Station
              target = Position(latitude = 41.8785576, longitude = -87.6338853),
              zoom = 8.0,
              padding = mapPadding,
            )
          )

        MaplibreMap(
          styleUrl = "https://tiles.openfreemap.org/styles/liberty",
          uiSettings = uiSettings(padding = mapPadding),
          cameraState = cameraState,
        ) {
          val routeSource =
            rememberGeoJsonSource(
              id = "amtrak-routes",
              shape = ShapeOptions.Url(Res.getUri("files/geojson/amtrak/routes.geojson")),
              options = GeoJsonOptions(tolerance = 0.001f),
            )
          val stationSource =
            rememberGeoJsonSource(
              id = "amtrak-stations",
              shape = ShapeOptions.Url(Res.getUri("files/geojson/amtrak/stations.geojson")),
            )

          Anchor.Below("boundary_3") { LineLayer(id = "amtrak-routes", source = routeSource) }
        }
      }
    }
  }
}
