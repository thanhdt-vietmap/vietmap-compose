package dev.sargunv.maplibrekmp.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cocoapods.MapLibre.MLNAltitudeForZoomLevel
import cocoapods.MapLibre.MLNFeatureProtocol
import cocoapods.MapLibre.MLNMapCamera
import cocoapods.MapLibre.MLNMapDebugCollisionBoxesMask
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapDebugTimestampsMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNZoomLevelForAltitude
import cocoapods.MapLibre.allowsTilting
import dev.sargunv.maplibrekmp.core.camera.CameraPosition
import dev.sargunv.maplibrekmp.core.data.XY
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGSize
import platform.UIKit.UIEdgeInsets
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UIGestureRecognizer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit

internal actual class PlatformMap private actual constructor() {
  private lateinit var impl: MLNMapView
  internal lateinit var size: CValue<CGSize>
  internal lateinit var layoutDir: LayoutDirection

  // hold strong references to things that the sdk keeps weak references to
  private val gestures = mutableListOf<Gesture<*>>()
  var delegate: MapViewDelegate? = null
    set(value) {
      field = value.also(impl::setDelegate)
    }

  internal constructor(
    impl: MLNMapView,
    size: CValue<CGSize>,
    layoutDir: LayoutDirection,
  ) : this() {
    this.impl = impl
    this.size = size
    this.layoutDir = layoutDir
  }

  inline fun <reified T : UIGestureRecognizer> addGestures(vararg gestures: Gesture<T>) {
    gestures.forEach { gesture ->
      if (gesture.isCooperative) {
        impl.gestureRecognizers!!.filterIsInstance<T>().forEach {
          gesture.recognizer.requireGestureRecognizerToFail(it)
        }
      }
      impl.addGestureRecognizer(gesture.recognizer)
      this.gestures.add(gesture)
    }
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

  actual var controlSettings
    get() =
      ControlSettings(
        isLogoEnabled = !impl.logoView.hidden,
        isAttributionEnabled = !impl.attributionButton.hidden,
        isCompassEnabled = !impl.compassView.hidden,
        isRotateGesturesEnabled = impl.rotateEnabled,
        isScrollGesturesEnabled = impl.scrollEnabled,
        isTiltGesturesEnabled = impl.allowsTilting,
        isZoomGesturesEnabled = impl.zoomEnabled,
      )
    set(value) {
      impl.logoView.hidden = !value.isLogoEnabled
      impl.attributionButton.hidden = !value.isAttributionEnabled
      impl.compassView.hidden = !value.isCompassEnabled
      impl.rotateEnabled = value.isRotateGesturesEnabled
      impl.scrollEnabled = value.isScrollGesturesEnabled
      impl.allowsTilting = value.isTiltGesturesEnabled
      impl.zoomEnabled = value.isZoomGesturesEnabled
    }

  private fun MLNMapCamera.toCameraPosition(paddingValues: PaddingValues) =
    CameraPosition(
      target = centerCoordinate.toPosition(),
      bearing = heading,
      tilt = pitch,
      zoom =
        MLNZoomLevelForAltitude(
          altitude = altitude,
          pitch = pitch,
          latitude = centerCoordinate.useContents { latitude },
          size = size,
        ),
      padding = paddingValues,
    )

  private fun CameraPosition.toMLNMapCamera(): MLNMapCamera {
    return MLNMapCamera().let {
      it.centerCoordinate = target.toCLLocationCoordinate2D()
      it.pitch = tilt
      it.heading = bearing
      it.altitude =
        MLNAltitudeForZoomLevel(
          zoomLevel = zoom,
          pitch = tilt,
          latitude = target.latitude,
          size = size,
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
      left = calculateLeftPadding(layoutDir).value.toDouble(),
      bottom = calculateBottomPadding().value.toDouble(),
      right = calculateRightPadding(layoutDir).value.toDouble(),
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

  actual fun positionFromScreenLocation(xy: XY): Position =
    impl.convertPoint(point = xy.toCGPoint(), toCoordinateFromView = null).toPosition()

  actual fun screenLocationFromPosition(position: Position): XY =
    impl.convertCoordinate(position.toCLLocationCoordinate2D(), toPointToView = null).toXY()

  actual fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature> =
    impl.visibleFeaturesAtPoint(CGPointMake(xy.x.toDouble(), xy.y.toDouble()), layerIds).map {
      (it as MLNFeatureProtocol).toFeature()
    }
}
