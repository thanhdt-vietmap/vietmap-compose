package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.ViewModel
import dev.sargunv.maplibrekmp.compose.AnchoredLayers
import dev.sargunv.maplibrekmp.compose.GeoJsonSource
import dev.sargunv.maplibrekmp.compose.LayerAnchor
import dev.sargunv.maplibrekmp.compose.LineLayer
import dev.sargunv.maplibrekmp.map.MaplibreMap
import dev.sargunv.maplibrekmp.map.MaplibreMapOptions
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.gtfs.GtfsSdk
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel

class TrainMapViewModel(private val gtfsSdk: GtfsSdk) : ViewModel() {
  private val _state = mutableStateOf(TrainMapState())
  val state: State<TrainMapState> = _state

  //  init {
  //    viewModelScope.launch(Dispatchers.IO) {
  //      _state.value = _state.value.copy(loading = true)
  //      gtfsSdk
  //        .refreshSchedule()
  //        .onSuccess { _state.value = _state.value.copy(loading = false) }
  //        .onFailure { _state.value = _state.value.copy(loading = false, error = it.message) }
  //    }
  //  }
}

data class TrainMapState(val loading: Boolean = false, val error: String? = null)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrainMap(sheetPadding: PaddingValues) {
  val viewModel = koinViewModel<TrainMapViewModel>()
  val state by remember { viewModel.state }

  val insetsPadding = WindowInsets.safeDrawing.asPaddingValues(LocalDensity.current)

  MaplibreMap(
    options =
      MaplibreMapOptions(
        style =
          MaplibreMapOptions.StyleOptions(url = Res.getUri("files/maplibre/style/positron.json")),
        ui =
          MaplibreMapOptions.UiOptions(
            padding =
              PaddingValues(
                start =
                  max(
                    8.dp + sheetPadding.calculateLeftPadding(LayoutDirection.Ltr),
                    insetsPadding.calculateLeftPadding(LayoutDirection.Ltr),
                  ),
                end =
                  max(
                    8.dp + sheetPadding.calculateRightPadding(LayoutDirection.Ltr),
                    insetsPadding.calculateRightPadding(LayoutDirection.Ltr),
                  ),
                top =
                  max(
                    8.dp + sheetPadding.calculateTopPadding(),
                    insetsPadding.calculateTopPadding(),
                  ),
                bottom =
                  max(
                    8.dp + sheetPadding.calculateBottomPadding(),
                    insetsPadding.calculateBottomPadding(),
                  ),
              )
          ),
      )
  ) {
    var sec by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
      while (true) {
        delay(16)
        sec += 16
      }
    }

    val color by remember(sec) { mutableStateOf(Color.hsl((sec / 15 % 360).toFloat(), 1.0f, 0.5f)) }

    GeoJsonSource(url = Res.getUri("files/geojson/amtrak/routes.geojson"), tolerance = 0.001f)
      .let { amtrakRoutes ->
        AnchoredLayers(LayerAnchor.Below("boundary_3")) {
          LineLayer(
            sourceId = amtrakRoutes,
            lineColor = const(Color.White),
            lineWidth = interpolate(exponential(const(2f)), zoom(), 0 to const(2f), 10 to const(4f)),
          )
          LineLayer(
            sourceId = amtrakRoutes,
            lineColor = const(color),
            lineWidth = interpolate(exponential(const(2f)), zoom(), 0 to const(1f), 10 to const(2f)),
          )
        }
      }
  }
}
