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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import dev.sargunv.maplibrekmp.compose.MaplibreMap
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.compose.layer.SymbolLayer
import dev.sargunv.maplibrekmp.compose.rememberCameraState
import dev.sargunv.maplibrekmp.compose.source.getBaseSource
import dev.sargunv.maplibrekmp.compose.uiSettings
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.expression.ExpressionScope
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

      val cameraState =
        rememberCameraState(
          CameraPosition(
            // Chicago Union Station
            target = Position(latitude = 41.8785576, longitude = -87.6338853),
            zoom = 8.0,
          )
        )

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
            Text(cameraState.position.zoom.toString())
          }
        },
      ) { sheetPadding ->
        val insetsPadding = safeDrawingInsets.asPaddingValues()

        val mapPadding =
          remember(sheetPadding, insetsPadding) {
            max(insetsPadding, sheetPadding + PaddingValues(8.dp))
          }

        MaplibreMap(
          styleUrl = "https://tiles.openfreemap.org/styles/liberty",
          uiSettings = uiSettings(padding = mapPadding),
          cameraState = cameraState,
        ) {
          val tiles = getBaseSource("openmaptiles")

          Anchor.Replace("label_country_1") {
            SymbolLayer(
              id = "label_country_1_modified",
              source = tiles,
              sourceLayer = "place",
              maxZoom = 9f,
              filter =
                all(
                  get<String>(const("class")) eq const("country"),
                  get<String>(const("rank")) eq const(1),
                ),
              textField =
                format(
                  case(
                    has(const("name:nonlatin")) then
                      concat(get(const("name:latin")), const(" / "), get(const("name:nonlatin"))),
                    fallback = coalesce(get(const("name_en")), get(const("name"))),
                  ) to ExpressionScope.FormatStyle()
                ),
              textFont = literal(listOf(const("Noto Sans Bold"))),
              textMaxWidth = const(6.25),
              textSize = interpolate(linear(), zoom(), 1 to const(9), 4 to const(17)),
              textColor = const(Color.Magenta),
              textHaloBlur = const(1),
              textHaloColor = const(Color.White),
              textHaloWidth = const(1),
            )
          }
        }
      }
    }
  }
}
