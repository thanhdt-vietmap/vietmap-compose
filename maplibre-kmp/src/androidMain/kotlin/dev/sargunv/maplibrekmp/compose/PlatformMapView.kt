package dev.sargunv.maplibrekmp.compose

import android.view.Gravity
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.sargunv.maplibrekmp.core.LatLng
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.correctedAndroidUri
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
internal actual fun PlatformMapView(
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
) {
  var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current

  val currentOnCameraMove by rememberUpdatedState(onCameraMove)
  val currentOnClick by rememberUpdatedState(onClick)
  val currentOnLongClick by rememberUpdatedState(onLongClick)

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).also { mapView ->
        observer = LifecycleEventObserver { _, event ->
          when (event) {
            Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
            Lifecycle.Event.ON_START -> mapView.onStart()
            Lifecycle.Event.ON_RESUME -> mapView.onResume()
            Lifecycle.Event.ON_PAUSE -> mapView.onPause()
            Lifecycle.Event.ON_STOP -> mapView.onStop()
            Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
            else -> throw IllegalStateException()
          }
        }

        mapView.getMapAsync { map ->
          map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END
          onMapLoaded(PlatformMap(map))
          map.addOnCameraMoveListener { currentOnCameraMove() }
          map.addOnMapClickListener {
            currentOnClick(LatLng(it.latitude, it.longitude))
            false
          }
          map.addOnMapLongClickListener {
            currentOnLongClick(LatLng(it.latitude, it.longitude))
            false
          }
        }
      }
    },
    update = { mapView ->
      mapView.getMapAsync { map ->
        with(density) {
          val top = uiPadding.calculateTopPadding().roundToPx()
          val bottom = uiPadding.calculateBottomPadding().roundToPx()
          val left = uiPadding.calculateLeftPadding(layoutDir).roundToPx()
          val right = uiPadding.calculateRightPadding(layoutDir).roundToPx()
          map.uiSettings.setAttributionMargins(left, top, right, bottom)
          map.uiSettings.setLogoMargins(left, top, right, bottom)
          map.uiSettings.setCompassMargins(left, top, right, bottom)
        }
        updateMap(PlatformMap(map))
        map.setStyle(styleUrl.correctedAndroidUri().toString()) { onStyleLoaded(Style(it)) }
      }
    },
    onRelease = { onRelease() },
  )

  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    observer?.let { lifecycle.addObserver(it) }
    onDispose { observer?.let { lifecycle.removeObserver(it) } }
  }
}
