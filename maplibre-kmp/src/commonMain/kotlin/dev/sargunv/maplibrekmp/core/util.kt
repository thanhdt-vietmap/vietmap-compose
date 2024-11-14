package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Immutable

@Immutable public data class LatLng(val latitude: Double, val longitude: Double)

@Immutable
public data class CameraPadding(
  val left: Double,
  val top: Double,
  val right: Double,
  val bottom: Double,
)
