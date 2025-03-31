package vn.vietmap.vietmapcompose.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import vn.vietmap.vietmapcompose.core.MapViewLifecycleObserver
import vn.vietmap.vietmapsdk.maps.MapView

@Composable
internal fun MapViewLifecycleEffect(mapView: MapView?) {
  if (mapView == null) return
  val observer = remember(mapView) { MapViewLifecycleObserver(mapView) }
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    lifecycle.addObserver(observer)
    onDispose { lifecycle.removeObserver(observer) }
  }
}
