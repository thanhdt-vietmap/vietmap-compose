package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position

@Composable
internal expect fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  uiPadding: PaddingValues,
  updateMap: (map: PlatformMap) -> Unit,
  onMapLoaded: (map: PlatformMap) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onReset: () -> Unit,
  onCameraMove: () -> Unit,
  onClick: (latLng: Position, xy: XY) -> Unit,
  onLongClick: (latLng: Position, xy: XY) -> Unit,
)
