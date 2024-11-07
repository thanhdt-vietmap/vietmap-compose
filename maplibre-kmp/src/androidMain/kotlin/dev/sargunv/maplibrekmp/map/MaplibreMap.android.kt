package dev.sargunv.maplibrekmp.map

import android.view.Gravity
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.sargunv.maplibrekmp.compose.MapNode
import dev.sargunv.maplibrekmp.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.StyleScope
import dev.sargunv.maplibrekmp.style.Style
import kotlinx.coroutines.awaitCancellation
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style as MLNStyle

@Composable
public actual fun MaplibreMap(
  modifier: Modifier,
  options: MaplibreMapOptions,
  styleContent: @Composable StyleScope.() -> Unit,
) {
  var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }
  var style by remember { mutableStateOf<MLNStyle?>(null) }
  val compositionContext = rememberCompositionContext()

  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapLibre.getInstance(context)
      MapView(context).apply {
        observer = LifecycleEventObserver { _, event ->
          when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate(null)
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            else -> throw IllegalStateException()
          }
        }
      }
    },
    update = { mapView ->
      mapView.getMapAsync { map ->
        map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END

        map.isDebugActive = true

        map.uiSettings.isLogoEnabled = options.ui.isLogoEnabled
        map.uiSettings.isAttributionEnabled = options.ui.isAttributionEnabled
        map.uiSettings.isCompassEnabled = options.ui.isCompassEnabled

        map.uiSettings.isTiltGesturesEnabled = options.ui.isTiltGesturesEnabled
        map.uiSettings.isZoomGesturesEnabled = options.ui.isZoomGesturesEnabled
        map.uiSettings.isRotateGesturesEnabled = options.ui.isRotateGesturesEnabled
        map.uiSettings.isScrollGesturesEnabled = options.ui.isScrollGesturesEnabled

        with(density) {
          val topUiPadding = options.ui.padding.calculateTopPadding().roundToPx()
          val bottomUiPadding = options.ui.padding.calculateBottomPadding().roundToPx()
          val leftUiPadding = options.ui.padding.calculateLeftPadding(layoutDir).roundToPx()
          val rightUiPadding = options.ui.padding.calculateRightPadding(layoutDir).roundToPx()

          map.uiSettings.setAttributionMargins(
            leftUiPadding,
            topUiPadding,
            rightUiPadding,
            bottomUiPadding,
          )
          map.uiSettings.setLogoMargins(
            leftUiPadding,
            topUiPadding,
            rightUiPadding,
            bottomUiPadding,
          )
          map.uiSettings.setCompassMargins(
            leftUiPadding,
            topUiPadding,
            rightUiPadding,
            bottomUiPadding,
          )
        }

        map.setStyle(options.style.url.correctedAndroidUri().toString()) { style = it }
      }
    },
  )

  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    observer?.let { lifecycle.addObserver(it) }
    onDispose { observer?.let { lifecycle.removeObserver(it) } }
  }

  LaunchedEffect(style) {
    style?.let { style ->
      val composition =
        Composition(MapNodeApplier(MapNode.StyleNode(Style(style))), compositionContext)
      composition.setContent { StyleScope.styleContent() }
      try {
        awaitCancellation()
      } finally {
        composition.dispose()
      }
    }
  }
}
