package dev.sargunv.maplibre.kmpp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class MapViewOptions(
    val style: StyleOptions = StyleOptions(),
    val ui: UiOptions = UiOptions(),
) {
    data class StyleOptions(
        val url: String = "https://demotiles.maplibre.org/style.json",
        val sources: Map<String, Source> = emptyMap(),
        val layers: List<Layer> = emptyList(),
    )

    data class UiOptions(
        val padding: PaddingValues = PaddingValues(8.dp),
        val isLogoEnabled: Boolean = true,
        val isAttributionEnabled: Boolean = true,
        val isCompassEnabled: Boolean = true,
        val isTiltGesturesEnabled: Boolean = true,
        val isZoomGesturesEnabled: Boolean = true,
        val isRotateGesturesEnabled: Boolean = true,
        val isScrollGesturesEnabled: Boolean = true,
    )
}

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    options: MapViewOptions = MapViewOptions(),
)

