package dev.sargunv.maplibrecompose.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import vn.vietmap.vietmapsdk.maps.MapView

internal class MapViewLifecycleObserver(private val mapView: MapView) : LifecycleEventObserver {
  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    when (event) {
      Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
      Lifecycle.Event.ON_START -> mapView.onStart()
      Lifecycle.Event.ON_RESUME -> mapView.onResume()
      Lifecycle.Event.ON_PAUSE -> mapView.onPause()
      Lifecycle.Event.ON_STOP -> mapView.onStop()
      Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
      Lifecycle.Event.ON_ANY -> {}
    }
  }
}
