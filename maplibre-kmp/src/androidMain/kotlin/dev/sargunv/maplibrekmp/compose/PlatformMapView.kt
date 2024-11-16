package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

internal class MapViewLifecycleObserver(private val mapView: MapView) : LifecycleEventObserver {
  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    when (event) {
      Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
      Lifecycle.Event.ON_START -> mapView.onStart()
      Lifecycle.Event.ON_RESUME -> mapView.onResume()
      Lifecycle.Event.ON_PAUSE -> mapView.onPause()
      Lifecycle.Event.ON_STOP -> mapView.onStop()
      Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
      Lifecycle.Event.ON_ANY -> {}
    }
  }
}

@Composable
internal fun MapViewLifecycleEffect(mapView: MapView) {
  val observer = remember(mapView) { MapViewLifecycleObserver(mapView) }
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    lifecycle.addObserver(observer)
    onDispose { lifecycle.removeObserver(observer) }
  }
}

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
  currentMap?.let { MapViewLifecycleEffect(it.mapView) }

  val currentOnCameraMove =
    remember(currentMap, onCameraMove) { { currentMap?.let { onCameraMove(it) } ?: Unit } }
  val currentOnClick =
    remember(currentMap, onClick) {
      { pos: Position, xy: XY -> currentMap?.let { onClick(it, pos, xy) } ?: Unit }
    }
  val currentOnLongClick =
    remember(currentMap, onLongClick) {
      { pos: Position, xy: XY -> currentMap?.let { onLongClick(it, pos, xy) } ?: Unit }
    }

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).also { mapView ->
        mapView.getMapAsync { mapLibreMap ->
          currentMap = PlatformMap(mapView, mapLibreMap, layoutDir, density)
        }
      }
    },
    update = { _ ->
      currentMap?.let { map ->
        map.layoutDir = layoutDir
        map.density = density

        map.mapLibreMap.let { mlnMap ->
          map.onCameraMove = currentOnCameraMove
          map.onClick = currentOnClick
          map.onLongClick = currentOnLongClick

          // TODO push this into the PlatformMap class
          if (styleUrl != lastStyleUrl) {
            lastStyleUrl = styleUrl
            mlnMap.setStyle(styleUrl.correctedAndroidUri().toString()) { mlnStyle ->
              currentOnStyleChanged(map, Style(mlnStyle))
            }
          }
        }

        updateMap(map)
      }
    },
    onReset = {
      currentOnReset()
      currentMap = null
    },
  )
}
