package dev.sargunv.maplibrekmp.internal.wrapper

import org.maplibre.android.maps.MapLibreMap

internal actual class MapControl private actual constructor() {
  private lateinit var impl: MapLibreMap

  internal constructor(mapView: MapLibreMap) : this() {
    impl = mapView
  }

  actual var isDebugEnabled
    get() = impl.isDebugActive
    set(value) {
      impl.isDebugActive = value
    }

  actual var isLogoEnabled
    get() = impl.uiSettings.isLogoEnabled
    set(value) {
      impl.uiSettings.isLogoEnabled = value
    }

  actual var isAttributionEnabled
    get() = impl.uiSettings.isAttributionEnabled
    set(value) {
      impl.uiSettings.isAttributionEnabled = value
    }

  actual var isCompassEnabled
    get() = impl.uiSettings.isCompassEnabled
    set(value) {
      impl.uiSettings.isCompassEnabled = value
    }

  actual var isRotateGesturesEnabled
    get() = impl.uiSettings.isRotateGesturesEnabled
    set(value) {
      impl.uiSettings.isRotateGesturesEnabled = value
    }

  actual var isScrollGesturesEnabled
    get() = impl.uiSettings.isScrollGesturesEnabled
    set(value) {
      impl.uiSettings.isScrollGesturesEnabled = value
    }

  actual var isTiltGesturesEnabled
    get() = impl.uiSettings.isTiltGesturesEnabled
    set(value) {
      impl.uiSettings.isTiltGesturesEnabled = value
    }

  actual var isZoomGesturesEnabled
    get() = impl.uiSettings.isZoomGesturesEnabled
    set(value) {
      impl.uiSettings.isZoomGesturesEnabled = value
    }
}
