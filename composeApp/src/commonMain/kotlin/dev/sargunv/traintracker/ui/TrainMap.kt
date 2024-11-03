package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sargunv.maplibrecompose.*
import dev.sargunv.traintracker.generated.Res
import dev.sargunv.traintracker.gtfs.GtfsSdk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel

class TrainMapViewModel(private val gtfsSdk: GtfsSdk) : ViewModel() {
  private val _state = mutableStateOf(TrainMapState())
  val state: State<TrainMapState> = _state

  init {
    viewModelScope.launch(Dispatchers.IO) {
      _state.value = _state.value.copy(loading = true)
      gtfsSdk
        .refreshSchedule(noCache = true)
        .onSuccess { _state.value = _state.value.copy(loading = false) }
        .onFailure { _state.value = _state.value.copy(loading = false, error = it.message) }
    }
  }
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
          MaplibreMapOptions.StyleOptions(
            url = Res.getUri("files/maplibre/style/positron.json"),
            sources =
              mapOf(
                "amtrak-geojson" to
                  Source.GeoJson(
                    url = Res.getUri("files/geojson/amtrak/routes.geojson"),
                    tolerance = 0.001f,
                  )
              ),
            layers =
              listOf(
                Layer(
                  id = "amtrak-route-lines-casing",
                  source = "amtrak-geojson",
                  below = "boundary_3",
                  type =
                    Layer.Type.Line(
                      color = Color(0xFF888888u),
                      width = 3f,
                      cap = "round",
                      join = "miter",
                    ),
                ),
                Layer(
                  id = "amtrak-route-lines-inner",
                  source = "amtrak-geojson",
                  above = "amtrak-route-lines-casing",
                  type =
                    Layer.Type.Line(
                      color = Color(0xFFCAE4F1u),
                      width = 2f,
                      cap = "round",
                      join = "miter",
                    ),
                ),
              ),
          ),
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
  )
}
