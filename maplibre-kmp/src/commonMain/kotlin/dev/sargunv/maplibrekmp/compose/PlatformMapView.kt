package dev.sargunv.maplibrekmp.compose

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
  update: (map: PlatformMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: PlatformMap, style: Style) -> Unit,
  onCameraMove: (map: PlatformMap) -> Unit,
  onClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
)
