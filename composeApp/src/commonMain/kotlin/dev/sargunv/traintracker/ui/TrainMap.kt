package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.ViewModel
import dev.sargunv.maplibrekmp.compose.MapUiSettings
import dev.sargunv.maplibrekmp.compose.MaplibreMap
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.compose.layer.CircleLayer
import dev.sargunv.maplibrekmp.compose.layer.CirclePaint
import dev.sargunv.maplibrekmp.compose.layer.LineLayer
import dev.sargunv.maplibrekmp.compose.layer.LinePaint
import dev.sargunv.maplibrekmp.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrekmp.core.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.source.Shape
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.gtfs.GtfsSdk
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

  MaplibreMap(
    styleUrl = Res.getUri("files/maplibre/style/positron.json"),
    uiSettings = MapUiSettings(uiPadding = uiPadding),
  ) {
    val routeSource =
      rememberGeoJsonSource(
        key = "amtrak-routes",
        shape = Shape.Url(Res.getUri("files/geojson/amtrak/routes.geojson")),
        options = GeoJsonOptions(tolerance = 0.001f),
      )
    val stationSource =
      rememberGeoJsonSource(
        key = "amtrak-stations",
        shape = Shape.Url(Res.getUri("files/geojson/amtrak/stations.geojson")),
      )

    Anchor.Below("boundary_3") {
      LineLayer(
        key = "routes-outline",
        source = routeSource,
        paint =
          LinePaint(
            color = const(Color.White),
            width = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
          ),
      )
      LineLayer(
        key = "routes-fill",
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
        key = "stations-bus",
        source = stationSource,
        filter = const("BUS") eq get<String>(const("StnType"), properties<String>()),
        minZoom = 3f,
        paint =
          CirclePaint(
            color = const(Color.Magenta),
            strokeColor = const(Color.White),
            radius = interpolate(exponential(const(2)), zoom(), 0 to const(1.5), 10 to const(3)),
            strokeWidth =
              interpolate(exponential(const(2)), zoom(), 0 to const(0.75), 10 to const(1.5)),
          ),
      )
      CircleLayer(
        key = "stations-train",
        source = stationSource,
        filter = const("TRAIN") eq get<String>(const("StnType"), properties<String>()),
        minZoom = 2f,
        paint =
          CirclePaint(
            color = const(Color.Magenta),
            strokeColor = const(Color.White),
            radius = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
            strokeWidth = interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
          ),
      )
    }
  }
}
