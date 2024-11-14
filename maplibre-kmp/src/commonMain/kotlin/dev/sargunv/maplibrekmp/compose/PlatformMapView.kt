package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.sargunv.maplibrekmp.core.LatLng
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style

@Composable
internal expect fun PlatformMapView(
  modifier: Modifier,
  uiPadding: PaddingValues,
  styleUrl: String,
  updateMap: (map: PlatformMap) -> Unit,
  onMapLoaded: (map: PlatformMap) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onRelease: () -> Unit,
  onCameraMove: () -> Unit,
  onClick: (pos: LatLng) -> Unit,
  onLongClick: (pos: LatLng) -> Unit,
)
