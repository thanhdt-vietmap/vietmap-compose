package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.sargunv.maplibrekmp.core.MaplibreMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position

@Composable
internal expect fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  update: (map: MaplibreMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: MaplibreMap, style: Style?) -> Unit,
  onCameraMove: (map: MaplibreMap) -> Unit,
  onClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
)
