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
import dev.sargunv.traintracker.gtfs.db.GtfsScheduleDb
import dev.sargunv.traintracker.gtfs.db.Shape
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class TrainMapViewModel(
    private val gtfsScheduleDb: GtfsScheduleDb
) : ViewModel() {
    private val _state = mutableStateOf(TrainMapState())
    val state: State<TrainMapState> = _state

    init {
        loadShapes()
    }

    fun loadShapes() {
        viewModelScope.launch {
            val shapes = gtfsScheduleDb.clearAndInsert()
            _state.value = _state.value.copy(shapes = shapes)
        }
    }
}

data class TrainMapState(
    val shapes: Map<String, List<Shape>> = emptyMap()
)

@Composable
fun TrainMap(sheetPadding: PaddingValues) {
    val viewModel = koinViewModel<TrainMapViewModel>()
    val state by remember { viewModel.state }

    println(state.shapes)

    val insetsPadding = WindowInsets.safeDrawing.asPaddingValues(LocalDensity.current)
    MapView(
        styleUrl = "https://tiles.openfreemap.org/styles/positron",
        uiPadding = PaddingValues(
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
        )
    )
}