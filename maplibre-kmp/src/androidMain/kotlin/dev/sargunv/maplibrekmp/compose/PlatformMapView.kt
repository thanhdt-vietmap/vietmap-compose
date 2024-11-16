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
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.correctedAndroidUri
import dev.sargunv.maplibrekmp.core.data.XY
import dev.sargunv.maplibrekmp.core.toPosition
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.MapView

@Composable
internal actual fun PlatformMapView(
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
) {
  var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }

  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current

  val margins =
    remember(uiPadding, layoutDir, density) {
      with(density) {
        listOf(
          uiPadding.calculateLeftPadding(layoutDir).roundToPx(),
          uiPadding.calculateTopPadding().roundToPx(),
          uiPadding.calculateRightPadding(layoutDir).roundToPx(),
          uiPadding.calculateBottomPadding().roundToPx(),
        )
      }
    }

  var lastStyleUrl by remember { mutableStateOf<String?>(null) }

  val currentOnStyleLoaded by rememberUpdatedState(onStyleLoaded)
  val currentOnReset by rememberUpdatedState(onReset)
  val currentOnCameraMove by rememberUpdatedState(onCameraMove)
  val currentOnClick by rememberUpdatedState(onClick)
  val currentOnLongClick by rememberUpdatedState(onLongClick)

  var maplibreMap by remember { mutableStateOf<MapLibreMap?>(null) }
  var platformMap by remember { mutableStateOf<PlatformMap?>(null) }

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
        mapView.getMapAsync { maplibreMap = it }
      }
    },
    update = { _ ->
      if (platformMap == null && maplibreMap != null) {
        val mlMap = maplibreMap!!
        val map = PlatformMap(mlMap, layoutDir)

        mlMap.addOnCameraMoveListener { currentOnCameraMove() }
        mlMap.addOnMapClickListener { coords ->
          val pos = coords.toPosition()
          currentOnClick(pos, map.screenLocationFromPosition(pos))
          false
        }
        mlMap.addOnMapLongClickListener { coords ->
          val pos = coords.toPosition()
          currentOnLongClick(pos, map.screenLocationFromPosition(pos))
          false
        }

        onMapLoaded(map)
      }

      platformMap?.let { map ->
        map.layoutDir = layoutDir
        updateMap(map)
      }

      maplibreMap?.let { map ->
        map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END
        map.uiSettings.setAttributionMargins(margins[0], margins[1], margins[2], margins[3])
        map.uiSettings.setLogoMargins(margins[0], margins[1], margins[2], margins[3])
        map.uiSettings.setCompassMargins(margins[0], margins[1], margins[2], margins[3])

        if (styleUrl != lastStyleUrl) {
          map.setStyle(styleUrl.correctedAndroidUri().toString()) {
            currentOnStyleLoaded(Style(it))
          }
          lastStyleUrl = styleUrl
        }
      }
    },
    onReset = {
      maplibreMap = null
      platformMap = null
      currentOnReset()
    },
  )

  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    observer?.let { lifecycle.addObserver(it) }
    onDispose { observer?.let { lifecycle.removeObserver(it) } }
  }
}
