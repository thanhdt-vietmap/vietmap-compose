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
import dev.sargunv.maplibrekmp.MaplibreMap
import dev.sargunv.maplibrekmp.style.layer.AnchoredLayers
import dev.sargunv.maplibrekmp.style.layer.CircleLayer
import dev.sargunv.maplibrekmp.style.layer.CirclePaint
import dev.sargunv.maplibrekmp.style.layer.LayerAnchor
import dev.sargunv.maplibrekmp.style.layer.LineLayer
import dev.sargunv.maplibrekmp.style.layer.LinePaint
import dev.sargunv.maplibrekmp.style.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.style.source.GeoJsonUrlSource
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

  val uiPadding =
    remember(sheetPadding, insetsPadding) {
      val start =
        max(
          8.dp + sheetPadding.calculateLeftPadding(LayoutDirection.Ltr),
          insetsPadding.calculateLeftPadding(LayoutDirection.Ltr),
        )
      val end =
        max(
          8.dp + sheetPadding.calculateRightPadding(LayoutDirection.Ltr),
          insetsPadding.calculateRightPadding(LayoutDirection.Ltr),
        )
      val top = max(8.dp + sheetPadding.calculateTopPadding(), insetsPadding.calculateTopPadding())
      val bottom =
        max(8.dp + sheetPadding.calculateBottomPadding(), insetsPadding.calculateBottomPadding())
      PaddingValues(start = start, end = end, top = top, bottom = bottom)
    }

  val style = remember { Res.getUri("files/maplibre/style/positron.json") }
  val amtrakRoutes = remember { Res.getUri("files/geojson/amtrak/routes.geojson") }
  val amtrakStations = remember { Res.getUri("files/geojson/amtrak/stations.geojson") }

  MaplibreMap(styleUrl = style, uiPadding = uiPadding) {
    var sec by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
      while (true) {
        delay(1600)
        sec += 1600
      }
    }

    val changingColor by
      remember(sec) { mutableStateOf(Color.hsl((sec / 15 % 360).toFloat(), 1.0f, 0.5f)) }

    GeoJsonUrlSource(dataUrl = amtrakRoutes, options = GeoJsonOptions(tolerance = 0.001f)) {
      amtrakRoutes ->
      AnchoredLayers(LayerAnchor.Below("boundary_3")) {
        LineLayer(
          source = amtrakRoutes,
          paint =
            LinePaint(
              lineColor = const(Color.White),
              lineWidth = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
            ),
        )
        LineLayer(
          source = amtrakRoutes,
          paint =
            LinePaint(
              lineColor = const(changingColor),
              lineWidth = interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
            ),
        )
      }
    }

    GeoJsonUrlSource(dataUrl = amtrakStations) { amtrakStations ->
      // bus stations
      CircleLayer(
        source = amtrakStations,
        filter = const("BUS") eq get<String>(const("StnType"), properties<String>()),
        minZoom = 8f,
        paint =
          CirclePaint(
            circleColor = const(changingColor),
            circleStrokeColor = const(Color.White),
            circleRadius =
              interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
            circleStrokeWidth =
              interpolate(exponential(const(2)), zoom(), 0 to const(0.5), 10 to const(1)),
          ),
      )

      // train stations
      CircleLayer(
        source = amtrakStations,
        filter = const("TRAIN") eq get<String>(const("StnType"), properties<String>()),
        minZoom = 3f,
        paint =
          CirclePaint(
            circleColor = const(changingColor),
            circleStrokeColor = const(Color.White),
            circleRadius =
              interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
            circleStrokeWidth =
              interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
          ),
      )
    }
  }
}
