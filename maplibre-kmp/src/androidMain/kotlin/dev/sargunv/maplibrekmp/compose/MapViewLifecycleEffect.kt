package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.sargunv.maplibrekmp.core.MapViewLifecycleObserver
import dev.sargunv.maplibrekmp.core.AndroidMap

@Composable
internal fun MapViewLifecycleEffect(map: AndroidMap?) {
  if (map == null) return
  val observer = remember(map.mapView) { MapViewLifecycleObserver(map.mapView) }
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(lifecycle, observer) {
    lifecycle.addObserver(observer)
    onDispose { lifecycle.removeObserver(observer) }
  }
}
