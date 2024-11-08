package dev.sargunv.maplibrekmp.internal.wrapper

import android.view.Gravity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
internal actual fun NativeMap(
  modifier: Modifier,
  uiPadding: PaddingValues,
  styleUrl: String,
  updateMap: (map: MapControl) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onRelease: () -> Unit,
) {
  var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).also {
        observer = LifecycleEventObserver { _, event ->
          when (event) {
            Lifecycle.Event.ON_CREATE -> it.onCreate(null)
            Lifecycle.Event.ON_START -> it.onStart()
            Lifecycle.Event.ON_RESUME -> it.onResume()
            Lifecycle.Event.ON_PAUSE -> it.onPause()
            Lifecycle.Event.ON_STOP -> it.onStop()
            Lifecycle.Event.ON_DESTROY -> it.onDestroy()
            else -> throw IllegalStateException()
          }
        }
      }
    },
    update = { mapView ->
      mapView.getMapAsync { map ->
        map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END

        with(density) {
          val top = uiPadding.calculateTopPadding().roundToPx()
          val bottom = uiPadding.calculateBottomPadding().roundToPx()
          val left = uiPadding.calculateLeftPadding(layoutDir).roundToPx()
          val right = uiPadding.calculateRightPadding(layoutDir).roundToPx()
          map.uiSettings.setAttributionMargins(left, top, right, bottom)
          map.uiSettings.setLogoMargins(left, top, right, bottom)
          map.uiSettings.setCompassMargins(left, top, right, bottom)
        }

        updateMap(MapControl(map))
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
