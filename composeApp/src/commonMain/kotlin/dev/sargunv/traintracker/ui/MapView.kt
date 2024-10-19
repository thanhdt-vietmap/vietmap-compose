package dev.sargunv.traintracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MapView(
    modifier: Modifier = Modifier,
    uiPadding: PaddingValues? = null,
    styleUrl: String = "https://demotiles.maplibre.org/style.json",
    isLogoEnabled: Boolean = true,
    isAttributionEnabled: Boolean = true,
    isCompassEnabled: Boolean = true,
    isTiltGesturesEnabled: Boolean = true,
    isZoomGesturesEnabled: Boolean = true,
    isRotateGesturesEnabled: Boolean = true,
    isScrollGesturesEnabled: Boolean = true,
)

