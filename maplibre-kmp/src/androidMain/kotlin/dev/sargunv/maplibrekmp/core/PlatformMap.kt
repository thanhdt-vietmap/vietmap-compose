package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.compose.CameraPosition
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.camera.CameraPosition as MLNCameraPosition
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

  private fun MLNCameraPosition.toCameraPosition(): CameraPosition =
    CameraPosition(
      target =
        target?.let { LatLng(it.latitude, it.longitude) }
          ?: LatLng(0.0, 0.0).also { println("target is null") },
      zoom = zoom,
      bearing = bearing,
      tilt = tilt,
    )

  private fun CameraPosition.toMLNCameraPosition(): MLNCameraPosition =
    MLNCameraPosition.Builder()
      .target(MLNLatLng(target.latitude, target.longitude))
      .zoom(zoom)
      .tilt(tilt)
      .bearing(bearing)
      .build()

  actual var cameraPosition: CameraPosition
    get() = impl.cameraPosition.toCameraPosition()
    set(value) {
      impl.cameraPosition = value.toMLNCameraPosition()
    }

  actual fun animateCameraPosition(finalPosition: CameraPosition) =
    impl.animateCamera(CameraUpdateFactory.newCameraPosition(finalPosition.toMLNCameraPosition()))

  private fun CameraPadding.toDoubleArray(): DoubleArray = doubleArrayOf(left, top, right, bottom)

  private fun DoubleArray.toCameraPadding(): CameraPadding =
    CameraPadding(get(0), get(1), get(2), get(3))

  actual var cameraPadding: CameraPadding
    get() = impl.cameraPosition.padding?.toCameraPadding() ?: CameraPadding()
    set(value) {
      impl.moveCamera(CameraUpdateFactory.paddingTo(value.toDoubleArray()))
    }

  actual fun animateCameraPadding(finalPadding: CameraPadding) =
    impl.animateCamera(CameraUpdateFactory.paddingTo(finalPadding.toDoubleArray()))
}
