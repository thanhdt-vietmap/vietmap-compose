package dev.sargunv.traintracker.ui

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
import kotlin.math.roundToInt

@Composable
actual fun MapView(
    modifier: Modifier,
    styleUrl: String,
    uiSettings: MapUiSettings,
) {
    // remember some objects related to the underlying MapView, set in the factory
    var observer by remember { mutableStateOf<LifecycleEventObserver?>(null) }

    // remember the latest values of the parameters, as they'll be used in the update lambda
    val updatedStyleUrl by rememberUpdatedState(styleUrl)
    val updatedUiSettings by rememberUpdatedState(uiSettings)

    val updatedDirection by rememberUpdatedState(LocalLayoutDirection.current)
    val updatedDensity by rememberUpdatedState(LocalDensity.current)

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
            mapView.applyUiSettings(updatedUiSettings, updatedDensity, updatedDirection)
            mapView.getMapAsync { map ->
                map.setStyle(updatedStyleUrl)
            }
        }
    )

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, observer) {
        observer?.let { lifecycle.addObserver(it) }
        onDispose { observer?.let { lifecycle.removeObserver(it) } }
    }
}

fun MapView.applyUiSettings(
    uiSettings: MapUiSettings,
    density: Density,
    dir: LayoutDirection
) {
    getMapAsync { map ->
        uiSettings.apply {
            map.uiSettings.isLogoEnabled = isLogoEnabled
            map.uiSettings.isAttributionEnabled = isAttributionEnabled
            map.uiSettings.isCompassEnabled = isCompassEnabled

            map.uiSettings.isTiltGesturesEnabled = isTiltGesturesEnabled
            map.uiSettings.isZoomGesturesEnabled = isZoomGesturesEnabled
            map.uiSettings.isRotateGesturesEnabled = isRotateGesturesEnabled
            map.uiSettings.isScrollGesturesEnabled = isScrollGesturesEnabled

            with(density) {
                val leftUiPadding =
                    padding.calculateLeftPadding(dir).toPx().roundToInt()
                val topUiPadding =
                    padding.calculateTopPadding().toPx().roundToInt()
                val rightUiPadding =
                    padding.calculateRightPadding(dir).toPx().roundToInt()
                val bottomUiPadding =
                    padding.calculateBottomPadding().toPx().roundToInt()

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
}
