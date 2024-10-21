package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class MapUiSettings(
    val padding: PaddingValues = PaddingValues(8.dp),
    val isLogoEnabled: Boolean = true,
    val isAttributionEnabled: Boolean = true,
    val isCompassEnabled: Boolean = true,
    val isTiltGesturesEnabled: Boolean = true,
    val isZoomGesturesEnabled: Boolean = true,
    val isRotateGesturesEnabled: Boolean = true,
    val isScrollGesturesEnabled: Boolean = true,
)

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    styleUrl: String = "https://demotiles.maplibre.org/style.json",
    uiSettings: MapUiSettings = MapUiSettings(),
)

