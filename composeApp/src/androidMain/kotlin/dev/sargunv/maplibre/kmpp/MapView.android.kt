package dev.sargunv.maplibre.kmpp

import android.view.Gravity
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
actual fun MapView(
    modifier: Modifier,
    options: MapViewOptions,
) {
    // remember some objects related to the underlying MapView, set in the factory
    var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }

    // AndroidView has some long-lived lambdas that need to reference the latest values
    val latestUiOptions by rememberUpdatedState(options.ui)
    val latestStyleOptions by rememberUpdatedState(options.style)
    val latestLayoutDir by rememberUpdatedState(LocalLayoutDirection.current)
    val latestDensity by rememberUpdatedState(LocalDensity.current)

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
                getMapAsync { map ->
                    map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END
                }
            }
        },
        update = { mapView ->
            mapView.applyUiOptions(latestUiOptions, latestDensity, latestLayoutDir)
            mapView.getMapAsync { map ->
                map.setStyle(latestStyleOptions.url)
            }
        }
    )

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, observer) {
        observer?.let { lifecycle.addObserver(it) }
        onDispose { observer?.let { lifecycle.removeObserver(it) } }
    }
}

fun MapView.applyUiOptions(
    options: MapViewOptions.UiOptions,
    density: Density,
    layoutDir: LayoutDirection
) {
    getMapAsync { map ->
        map.uiSettings.isLogoEnabled = options.isLogoEnabled
        map.uiSettings.isAttributionEnabled = options.isAttributionEnabled
        map.uiSettings.isCompassEnabled = options.isCompassEnabled

        map.uiSettings.isTiltGesturesEnabled = options.isTiltGesturesEnabled
        map.uiSettings.isZoomGesturesEnabled = options.isZoomGesturesEnabled
        map.uiSettings.isRotateGesturesEnabled = options.isRotateGesturesEnabled
        map.uiSettings.isScrollGesturesEnabled = options.isScrollGesturesEnabled

        with(density) {
            val topUiPadding = options.padding.calculateTopPadding().roundToPx()
            val bottomUiPadding = options.padding.calculateBottomPadding().roundToPx()
            val leftUiPadding = options.padding.calculateLeftPadding(layoutDir).roundToPx()
            val rightUiPadding = options.padding.calculateRightPadding(layoutDir).roundToPx()

            map.uiSettings.setAttributionMargins(
                leftUiPadding,
                topUiPadding,
                rightUiPadding,
                bottomUiPadding
            )
            map.uiSettings.setLogoMargins(
                leftUiPadding,
                topUiPadding,
                rightUiPadding,
                bottomUiPadding
            )
            map.uiSettings.setCompassMargins(
                leftUiPadding,
                topUiPadding,
                rightUiPadding,
                bottomUiPadding
            )
        }
    }
}
