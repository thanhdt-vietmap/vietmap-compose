package dev.sargunv.maplibrekmp.core

import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.geometry.LatLng as MLNLatLng

internal actual class PlatformMap private actual constructor() {
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

  actual var cameraBearing
    get() = impl.cameraPosition.bearing
    set(value) = impl.moveCamera(CameraUpdateFactory.bearingTo(value))

  actual var cameraPadding: CameraPadding
    get() =
      impl.cameraPosition.padding?.let {
        CameraPadding(left = it[0], top = it[1], right = it[2], bottom = it[3])
      } ?: CameraPadding(0.0, 0.0, 0.0, 0.0)
    set(value) =
      impl.moveCamera(
        CameraUpdateFactory.paddingTo(
          doubleArrayOf(value.left, value.top, value.right, value.bottom)
        )
      )

  actual var cameraTarget: LatLng
    get() =
      impl.cameraPosition.target?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(0.0, 0.0)
    set(value) =
      impl.moveCamera(CameraUpdateFactory.newLatLng(MLNLatLng(value.latitude, value.longitude)))

  actual var cameraTilt: Double
    get() = impl.cameraPosition.tilt
    set(value) = impl.moveCamera(CameraUpdateFactory.tiltTo(value))

  actual var cameraZoom: Double
    get() = impl.cameraPosition.zoom
    set(value) = impl.moveCamera(CameraUpdateFactory.zoomTo(value))
}
