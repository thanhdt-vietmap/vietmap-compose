package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Immutable

@Immutable public data class LatLng(val latitude: Double, val longitude: Double)

@Immutable
public data class CameraPadding(
  val left: Double = 0.0,
  val top: Double = 0.0,
  val right: Double = 0.0,
  val bottom: Double = 0.0,
)
