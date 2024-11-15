package dev.sargunv.maplibrekmp.core.camera

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.core.LatLng

@Immutable
public data class CameraPosition(
  public val bearing: Double = 0.0,
  public val target: LatLng = LatLng(0.0, 0.0),
  public val tilt: Double = 0.0,
  public val zoom: Double = 1.0,
  public val padding: PaddingValues = PaddingValues(0.dp),
)
