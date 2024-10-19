package dev.sargunv.traintracker.ui

import android.view.Gravity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.maplibre.android.MapLibre
import org.maplibre.android.maps.MapView

@Composable
actual fun NativeMap(modifier: Modifier, uiPadding: PaddingValues) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val (mapView, setMapView) = remember { mutableStateOf<MapView?>(null) }

    val (leftUiPadding, topUiPadding, rightUiPadding, bottomUiPadding) = paddingValuesToPx(uiPadding)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapLibre.getInstance(context)
            MapView(context).apply(setMapView)
        },
        update = { view ->
            view.getMapAsync { map ->
                map.setStyle("https://tiles.openfreemap.org/styles/positron")
                map.uiSettings.apply {
                    attributionGravity = Gravity.BOTTOM or Gravity.END
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