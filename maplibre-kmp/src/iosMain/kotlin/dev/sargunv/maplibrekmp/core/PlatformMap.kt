package dev.sargunv.maplibrekmp.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cocoapods.MapLibre.MLNAltitudeForZoomLevel
import cocoapods.MapLibre.MLNMapCamera
import cocoapods.MapLibre.MLNMapDebugCollisionBoxesMask
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapDebugTimestampsMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNZoomLevelForAltitude
import cocoapods.MapLibre.allowsTilting
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSize
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIEdgeInsets
import platform.UIKit.UIEdgeInsetsMake
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit

internal actual class PlatformMap private actual constructor() {
  private lateinit var impl: MLNMapView
  //  internal var mapViewSize: CValue<CGSize> = CGSizeMake(0.0, 0.0)
  internal lateinit var mapViewSize: CValue<CGSize>
  internal var layoutDirection: LayoutDirection = LayoutDirection.Ltr

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

  private fun MLNMapCamera.toCameraPosition(paddingValues: PaddingValues) =
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
      padding = paddingValues,
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
      it
    }
  }

  actual var cameraPosition: CameraPosition
    get() =
      impl.camera.toCameraPosition(
        paddingValues =
          impl.cameraEdgeInsets.useContents {
            PaddingValues.Absolute(
              left = left.dp,
              top = top.dp,
              right = right.dp,
              bottom = bottom.dp,
            )
          }
      )
    set(value) =
      impl.setCamera(
        value.toMLNMapCamera(),
        withDuration = 0.0,
        animationTimingFunction = null,
        edgePadding = value.padding.toEdgeInsets(),
        completionHandler = null,
      )

  private fun PaddingValues.toEdgeInsets(): CValue<UIEdgeInsets> =
    UIEdgeInsetsMake(
      top = calculateTopPadding().value.toDouble(),
      left = calculateLeftPadding(layoutDirection).value.toDouble(),
      bottom = calculateBottomPadding().value.toDouble(),
      right = calculateRightPadding(layoutDirection).value.toDouble(),
    )

  actual suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration) =
    suspendCoroutine { cont ->
      impl.flyToCamera(
        finalPosition.toMLNMapCamera(),
        withDuration = duration.toDouble(DurationUnit.SECONDS),
        edgePadding = finalPosition.padding.toEdgeInsets(),
        completionHandler = { cont.resume(Unit) },
      )
    }
}
