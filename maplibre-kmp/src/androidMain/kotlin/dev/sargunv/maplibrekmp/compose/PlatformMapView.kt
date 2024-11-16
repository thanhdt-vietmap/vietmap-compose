package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  updateMap: (map: PlatformMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: PlatformMap, style: Style) -> Unit,
  onCameraMove: (map: PlatformMap) -> Unit,
  onClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current

  var lastStyleUrl by remember { mutableStateOf<String?>(null) }

  val currentOnReset by rememberUpdatedState(onReset)
  val currentOnStyleChanged by rememberUpdatedState(onStyleChanged)

  var currentMap by remember { mutableStateOf<PlatformMap?>(null) }

  MapViewLifecycleEffect(currentMap)

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).apply {
        getMapAsync { map -> currentMap = PlatformMap(this, map, layoutDir, density) }
      }
    },
    update = { _ ->
      val map = currentMap ?: return@AndroidView

      map.layoutDir = layoutDir
      map.density = density
      map.onCameraMove = onCameraMove
      map.onClick = onClick
      map.onLongClick = onLongClick

      // TODO push this into the PlatformMap class
      if (styleUrl != lastStyleUrl) {
        lastStyleUrl = styleUrl
        map.mapLibreMap.setStyle(styleUrl.correctedAndroidUri().toString()) { mlnStyle ->
          currentOnStyleChanged(map, Style(mlnStyle))
        }
      }

      updateMap(map)
    },
    onReset = {
      currentOnReset()
      currentMap = null
    },
  )
}
