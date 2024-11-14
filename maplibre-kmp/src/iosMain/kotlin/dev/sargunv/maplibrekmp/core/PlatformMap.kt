package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNMapDebugCollisionBoxesMask
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapDebugTimestampsMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.allowsTilting
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIEdgeInsetsMake

internal actual class PlatformMap private actual constructor() {
  private lateinit var impl: MLNMapView

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

  actual var cameraBearing: Double
    get() = impl.direction
    set(value) {
      impl.direction = value
    }

  actual var cameraPadding: CameraPadding
    get() =
      impl.contentInset.useContents {
        CameraPadding(left = left, top = top, right = right, bottom = bottom)
      }
    set(value) {
      impl.contentInset =
        UIEdgeInsetsMake(
          left = value.left,
          top = value.top,
          right = value.right,
          bottom = value.bottom,
        )
    }

  actual var cameraTarget: LatLng
    get() = impl.centerCoordinate.useContents { LatLng(latitude, longitude) }
    set(value) {
      impl.centerCoordinate = CLLocationCoordinate2DMake(value.latitude, value.longitude)
    }

  actual var cameraTilt: Double
    get() = impl.camera.pitch
    set(value) {
      impl.camera.pitch = value
    }

  actual var cameraZoom: Double
    get() = impl.zoomLevel
    set(value) {
      impl.zoomLevel = value
    }
}
