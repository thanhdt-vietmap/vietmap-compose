package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNAltitudeForZoomLevel
import cocoapods.MapLibre.MLNMapCamera
import cocoapods.MapLibre.MLNMapDebugCollisionBoxesMask
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapDebugTimestampsMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNZoomLevelForAltitude
import cocoapods.MapLibre.allowsTilting
import dev.sargunv.maplibrekmp.compose.CameraPosition
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSize
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIEdgeInsets
import platform.UIKit.UIEdgeInsetsMake

internal actual class PlatformMap private actual constructor() {
  private lateinit var impl: MLNMapView
  internal lateinit var mapViewSize: CValue<CGSize>

  internal constructor(impl: MLNMapView) : this() {
    this.impl = impl
  }

  actual var isDebugEnabled: Boolean
    get() = impl.debugMask != 0uL
    set(value) {
      impl.debugMask =
        if (value)
          MLNMapDebugTileBoundariesMask or
            MLNMapDebugTileInfoMask or
            MLNMapDebugTimestampsMask or
            MLNMapDebugCollisionBoxesMask
        else 0uL
    }

  actual var isLogoEnabled: Boolean
    get() = !impl.logoView.hidden
    set(value) {
      impl.logoView.hidden = !value
    }

  actual var isAttributionEnabled: Boolean
    get() = !impl.attributionButton.hidden
    set(value) {
      impl.attributionButton.hidden = !value
    }

  actual var isCompassEnabled: Boolean
    get() = !impl.compassView.hidden
    set(value) {
      impl.compassView.hidden = !value
    }

  actual var isRotateGesturesEnabled: Boolean
    get() = impl.rotateEnabled
    set(value) {
      impl.rotateEnabled = value
    }

  actual var isScrollGesturesEnabled: Boolean
    get() = impl.scrollEnabled
    set(value) {
      impl.scrollEnabled = value
    }

  actual var isTiltGesturesEnabled: Boolean
    get() = impl.allowsTilting
    set(value) {
      impl.allowsTilting = value
    }

  actual var isZoomGesturesEnabled: Boolean
    get() = impl.zoomEnabled
    set(value) {
      impl.zoomEnabled = value
    }

  private fun MLNMapCamera.toCameraPosition() =
    CameraPosition(
      target = centerCoordinate.useContents { LatLng(latitude, longitude) },
      bearing = heading,
      tilt = pitch,
      zoom =
        MLNZoomLevelForAltitude(
          altitude = altitude,
          pitch = pitch,
          latitude = centerCoordinate.useContents { latitude },
          size = mapViewSize,
        ),
    )

  private fun CameraPosition.toMLNMapCamera(): MLNMapCamera {
    return MLNMapCamera().let {
      it.centerCoordinate = CLLocationCoordinate2DMake(target.latitude, target.longitude)
      it.pitch = tilt
      it.heading = bearing
      it.altitude =
        MLNAltitudeForZoomLevel(
            zoomLevel = zoom,
            pitch = tilt,
            latitude = target.latitude,
            size = mapViewSize,
          )
          .also {
            println(
              "Altitude: $it for zoom level $zoom, pitch $tilt, latitude ${target.latitude}, size ${mapViewSize.useContents { width to height }}"
            )
          }
      it
    }
  }

  actual var cameraPosition: CameraPosition
    get() = impl.camera.toCameraPosition()
    set(value) = impl.setCamera(value.toMLNMapCamera(), animated = false)

  actual fun animateCameraPosition(finalPosition: CameraPosition) =
    impl.setCamera(finalPosition.toMLNMapCamera(), animated = true)

  private fun UIEdgeInsets.toCameraPadding() =
    CameraPadding(left = left, top = top, right = right, bottom = bottom)

  private fun CameraPadding.toUIEdgeInsets() =
    UIEdgeInsetsMake(left = left, top = top, right = right, bottom = bottom)

  actual var cameraPadding: CameraPadding
    get() = impl.contentInset.useContents { toCameraPadding() }
    set(value) {
      impl.contentInset = value.toUIEdgeInsets()
    }

  actual fun animateCameraPadding(finalPadding: CameraPadding) =
    impl.setContentInset(finalPadding.toUIEdgeInsets(), animated = true)
}
