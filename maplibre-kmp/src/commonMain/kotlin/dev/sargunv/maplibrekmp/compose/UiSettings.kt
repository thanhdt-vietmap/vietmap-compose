package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.data.UiSettings

@Composable
public fun uiSettings(
  padding: PaddingValues = WindowInsets.safeDrawing.asPaddingValues(),
  isLogoEnabled: Boolean = true,
  isAttributionEnabled: Boolean = true,
  isCompassEnabled: Boolean = true,
  isMapToolbarEnabled: Boolean = true,
  isTiltGesturesEnabled: Boolean = true,
  isZoomGesturesEnabled: Boolean = true,
  isRotateGesturesEnabled: Boolean = true,
  isScrollGesturesEnabled: Boolean = true,
): UiSettings =
  UiSettings(
    padding = padding,
    isLogoEnabled = isLogoEnabled,
    isAttributionEnabled = isAttributionEnabled,
    isCompassEnabled = isCompassEnabled,
    isMapToolbarEnabled = isMapToolbarEnabled,
    isTiltGesturesEnabled = isTiltGesturesEnabled,
    isZoomGesturesEnabled = isZoomGesturesEnabled,
    isRotateGesturesEnabled = isRotateGesturesEnabled,
    isScrollGesturesEnabled = isScrollGesturesEnabled,
  )
