package dev.sargunv.maplibrekmp.core

public data class ControlSettings(
  val isLogoEnabled: Boolean = true,
  val isAttributionEnabled: Boolean = true,
  val isCompassEnabled: Boolean = true,
  val isMapToolbarEnabled: Boolean = true,
  val isTiltGesturesEnabled: Boolean = true,
  val isZoomGesturesEnabled: Boolean = true,
  val isRotateGesturesEnabled: Boolean = true,
  val isScrollGesturesEnabled: Boolean = true,
)
