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
import dev.sargunv.maplibre.kmpp.Layer
import dev.sargunv.maplibre.kmpp.MapView
import dev.sargunv.maplibre.kmpp.MapViewOptions
import dev.sargunv.maplibre.kmpp.Source
import dev.sargunv.traintracker.gtfs.db.GtfsScheduleDb
import dev.sargunv.traintracker.gtfs.db.Shape
import org.koin.compose.viewmodel.koinViewModel

class TrainMapViewModel(
    private val gtfsScheduleDb: GtfsScheduleDb
) : ViewModel() {
    private val _state = mutableStateOf(TrainMapState())
    val state: State<TrainMapState> = _state
}

data class TrainMapState(
    val shapes: Map<String, List<Shape>> = emptyMap()
)

@Composable
fun TrainMap(sheetPadding: PaddingValues) {
    val viewModel = koinViewModel<TrainMapViewModel>()
    val state by remember { viewModel.state }

    val insetsPadding = WindowInsets.safeDrawing.asPaddingValues(LocalDensity.current)
    MapView(
        options = MapViewOptions(
            style = MapViewOptions.StyleOptions(
                url = "https://tiles.openfreemap.org/styles/positron",
                sources = mapOf(
                    "amtrak-geojson" to Source.GeoJson(
                        url = "https://raw.githubusercontent.com/datanews/amtrak-geojson/master/amtrak-combined.geojson",
                        tolerance = 0.001f
                    )
                ),
                layers = listOf(
                    Layer(
                        id = "amtrak-route-lines",
                        source = "amtrak-geojson",
                        type = Layer.Type.Line(
                            color = 0xFFCAE4F1.toInt(),
                            width = 2f,
                            cap = "round",
                            join = "round",
                        )
                    )
                ),
            ),
            ui = MapViewOptions.UiOptions(
                padding = PaddingValues(
                    start = max(
                        8.dp + sheetPadding.calculateLeftPadding(LayoutDirection.Ltr),
                        insetsPadding.calculateLeftPadding(LayoutDirection.Ltr)
                    ),
                    end = max(
                        8.dp + sheetPadding.calculateRightPadding(LayoutDirection.Ltr),
                        insetsPadding.calculateRightPadding(LayoutDirection.Ltr)
                    ),
                    top = max(
                        8.dp + sheetPadding.calculateTopPadding(),
                        insetsPadding.calculateTopPadding()
                    ),
                    bottom = max(
                        8.dp + sheetPadding.calculateBottomPadding(),
                        insetsPadding.calculateBottomPadding()
                    )
                ),
            ),
        ),
    )
}