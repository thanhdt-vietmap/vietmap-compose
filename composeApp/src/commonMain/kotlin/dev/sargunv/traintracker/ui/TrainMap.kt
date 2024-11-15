package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dev.sargunv.maplibrekmp.compose.MaplibreMap
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.compose.layer.CircleLayer
import dev.sargunv.maplibrekmp.compose.layer.CirclePaint
import dev.sargunv.maplibrekmp.compose.layer.LineLayer
import dev.sargunv.maplibrekmp.compose.layer.LinePaint
import dev.sargunv.maplibrekmp.compose.rememberCameraState
import dev.sargunv.maplibrekmp.compose.source.rememberGeoJsonSource
import dev.sargunv.maplibrekmp.core.source.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.source.Shape
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.gtfs.GtfsSdk
import io.github.dellisd.spatialk.geojson.Point
import kotlinx.coroutines.launch
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
fun TrainMap(uiPadding: PaddingValues) {
  val viewModel = koinViewModel<TrainMapViewModel>()
  val state by remember { viewModel.state }

  val cameraState = rememberCameraState()
  val coroutineScope = rememberCoroutineScope()

  MaplibreMap(
    styleUrl = "https://tiles.openfreemap.org/styles/liberty",
    uiPadding = uiPadding,
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
            radius = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
            strokeWidth = interpolate(exponential(const(2)), zoom(), 0 to const(1), 10 to const(2)),
          ),
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
            radius = interpolate(exponential(const(2)), zoom(), 0 to const(4), 10 to const(8)),
            strokeWidth = interpolate(exponential(const(2)), zoom(), 0 to const(2), 10 to const(4)),
          ),
        onClick = { features ->
          val pos = features.map { it.geometry }.filterIsInstance<Point>().first().coordinates
          coroutineScope.launch { cameraState.animateTo(cameraState.position.copy(target = pos)) }
        },
      )
    }
  }
}
