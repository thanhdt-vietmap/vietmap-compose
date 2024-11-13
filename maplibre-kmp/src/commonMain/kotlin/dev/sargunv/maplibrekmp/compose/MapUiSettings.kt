package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

public data class MapUiSettings(
  val uiPadding: PaddingValues = PaddingValues(8.dp),
  val isDebugEnabled: Boolean = false,
  val isLogoEnabled: Boolean = true,
  val isAttributionEnabled: Boolean = true,
  val isCompassEnabled: Boolean = true,
  val isTiltGesturesEnabled: Boolean = true,
  val isZoomGesturesEnabled: Boolean = true,
  val isRotateGesturesEnabled: Boolean = true,
  val isScrollGesturesEnabled: Boolean = true,
)
