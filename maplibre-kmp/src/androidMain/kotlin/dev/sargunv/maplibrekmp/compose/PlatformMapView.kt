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
import dev.sargunv.maplibrekmp.core.AndroidMap
import dev.sargunv.maplibrekmp.core.MaplibreMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  update: (map: MaplibreMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: MaplibreMap, style: Style?) -> Unit,
  onCameraMove: (map: MaplibreMap) -> Unit,
  onClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current
  val currentOnReset by rememberUpdatedState(onReset)

  var currentMapView by remember { mutableStateOf<MapView?>(null) }
  var currentMap by remember { mutableStateOf<AndroidMap?>(null) }

  MapViewLifecycleEffect(currentMapView)

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).also { mapView ->
        currentMapView = mapView
        mapView.getMapAsync { map ->
          currentMap =
            AndroidMap(
              map = map,
              layoutDir = layoutDir,
              density = density,
              onStyleChanged = onStyleChanged,
              onCameraMove = onCameraMove,
              onClick = onClick,
              onLongClick = onLongClick,
              styleUrl = styleUrl,
            )
        }
      }
    },
    update = { _ ->
      val map = currentMap ?: return@AndroidView
      map.layoutDir = layoutDir
      map.density = density
      map.onStyleChanged = onStyleChanged
      map.onCameraMove = onCameraMove
      map.onClick = onClick
      map.onLongClick = onLongClick
      map.styleUrl = styleUrl
      update(map)
    },
    onReset = {
      currentOnReset()
      currentMap = null
      currentMapView = null
    },
  )
}
