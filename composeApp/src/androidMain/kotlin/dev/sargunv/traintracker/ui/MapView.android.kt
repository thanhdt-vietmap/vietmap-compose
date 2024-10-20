package dev.sargunv.traintracker.ui

import android.view.Gravity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
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
    uiPadding: PaddingValues?,
    styleUrl: String,
    isLogoEnabled: Boolean,
    isAttributionEnabled: Boolean,
    isCompassEnabled: Boolean,
    isTiltGesturesEnabled: Boolean,
    isZoomGesturesEnabled: Boolean,
    isRotateGesturesEnabled: Boolean,
    isScrollGesturesEnabled: Boolean,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val (mapView, setMapView) = remember { mutableStateOf<MapView?>(null) }

    val dir = LocalLayoutDirection.current
    val density = LocalDensity.current


    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapLibre.getInstance(context)
            MapView(context).apply(setMapView)
        },
        update = { view ->
            view.getMapAsync { map ->
                map.setStyle(styleUrl)

                map.uiSettings.apply {
                    this.isLogoEnabled = isLogoEnabled
                    this.isAttributionEnabled = isAttributionEnabled
                    this.isCompassEnabled = isCompassEnabled

                    this.isTiltGesturesEnabled = isTiltGesturesEnabled
                    this.isZoomGesturesEnabled = isZoomGesturesEnabled
                    this.isRotateGesturesEnabled = isRotateGesturesEnabled
                    this.isScrollGesturesEnabled = isScrollGesturesEnabled

                    attributionGravity = Gravity.BOTTOM or Gravity.END

                    if (uiPadding != null) {
                        with(density) {
                            val leftUiPadding =
                                uiPadding.calculateLeftPadding(dir).toPx().roundToInt()
                            val topUiPadding =
                                uiPadding.calculateTopPadding().toPx().roundToInt()
                            val rightUiPadding =
                                uiPadding.calculateRightPadding(dir).toPx().roundToInt()
                            val bottomUiPadding =
                                uiPadding.calculateBottomPadding().toPx().roundToInt()

                            setAttributionMargins(
                                leftUiPadding,
                                topUiPadding,
                                rightUiPadding,
                                bottomUiPadding
                            )
                            setLogoMargins(
                                leftUiPadding,
                                topUiPadding,
                                rightUiPadding,
                                bottomUiPadding
                            )
                            setCompassMargins(
                                leftUiPadding,
                                topUiPadding,
                                rightUiPadding,
                                bottomUiPadding
                            )
                        }
                    }
                }
            }
        }
    )

    DisposableEffect(lifecycle, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView?.onCreate(null)
                Lifecycle.Event.ON_START -> mapView?.onStart()
                Lifecycle.Event.ON_RESUME -> mapView?.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView?.onPause()
                Lifecycle.Event.ON_STOP -> mapView?.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView?.onDestroy()
                else -> throw IllegalStateException()
            }
        }
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}