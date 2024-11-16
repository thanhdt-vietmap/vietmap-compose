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
  private lateinit var mapView: MLNMapView
  internal lateinit var size: CValue<CGSize>
  internal lateinit var layoutDir: LayoutDirection
  internal lateinit var insetPadding: PaddingValues

  private lateinit var lastUiPadding: PaddingValues

  // hold strong references to things that the sdk keeps weak references to
  private val gestures = mutableListOf<Gesture<*>>()
  var delegate: MapViewDelegate? = null
    set(value) {
      mapView.delegate = value
      field = value
    }

  internal constructor(
    impl: MLNMapView,
    size: CValue<CGSize>,
    layoutDir: LayoutDirection,
    insetPadding: PaddingValues,
  ) : this() {
    this.mapView = impl
    this.size = size
    this.layoutDir = layoutDir
    this.insetPadding = insetPadding
  }

  inline fun <reified T : UIGestureRecognizer> addGestures(vararg gestures: Gesture<T>) {
    gestures.forEach { gesture ->
      if (gesture.isCooperative) {
        mapView.gestureRecognizers!!.filterIsInstance<T>().forEach {
          gesture.recognizer.requireGestureRecognizerToFail(it)
        }
      }
      mapView.addGestureRecognizer(gesture.recognizer)
      this.gestures.add(gesture)
    }
  }

  actual var isDebugEnabled: Boolean
    get() = mapView.debugMask != 0uL
    set(value) {
      mapView.debugMask =
        if (value)
          MLNMapDebugTileBoundariesMask or
            MLNMapDebugTileInfoMask or
            MLNMapDebugTimestampsMask or
            MLNMapDebugCollisionBoxesMask
        else 0uL
    }

  actual var uiSettings
    get() =
      UiSettings(
        padding = lastUiPadding,
        isLogoEnabled = !mapView.logoView.hidden,
        isAttributionEnabled = !mapView.attributionButton.hidden,
        isCompassEnabled = !mapView.compassView.hidden,
        isRotateGesturesEnabled = mapView.rotateEnabled,
        isScrollGesturesEnabled = mapView.scrollEnabled,
        isTiltGesturesEnabled = mapView.allowsTilting,
        isZoomGesturesEnabled = mapView.zoomEnabled,
      )
    set(value) {
      if (!::lastUiPadding.isInitialized || value.padding != lastUiPadding) {
        lastUiPadding = value.padding
        val topSafeInset = insetPadding.calculateTopPadding().value
        val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
        val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
        val bottomSafeInset = insetPadding.calculateBottomPadding().value

        val topUiPadding = value.padding.calculateTopPadding().value - topSafeInset // TODO gravity
        val leftUiPadding = value.padding.calculateLeftPadding(layoutDir).value - leftSafeInset
        val rightUiPadding = value.padding.calculateRightPadding(layoutDir).value - rightSafeInset
        val bottomUiPadding = value.padding.calculateBottomPadding().value - bottomSafeInset

        mapView.setLogoViewMargins(
          CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble())
        )
        mapView.setAttributionButtonMargins(
          CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble())
        )
      }
      mapView.logoView.hidden = !value.isLogoEnabled
      mapView.attributionButton.hidden = !value.isAttributionEnabled
      mapView.compassView.hidden = !value.isCompassEnabled
      mapView.rotateEnabled = value.isRotateGesturesEnabled
      mapView.scrollEnabled = value.isScrollGesturesEnabled
      mapView.allowsTilting = value.isTiltGesturesEnabled
      mapView.zoomEnabled = value.isZoomGesturesEnabled
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
      mapView.camera.toCameraPosition(
        paddingValues =
          mapView.cameraEdgeInsets.useContents {
            PaddingValues.Absolute(
              left = left.dp,
              top = top.dp,
              right = right.dp,
              bottom = bottom.dp,
            )
          }
      )
    set(value) =
      mapView.setCamera(
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
      mapView.flyToCamera(
        finalPosition.toMLNMapCamera(),
        withDuration = duration.toDouble(DurationUnit.SECONDS),
        edgePadding = finalPosition.padding.toEdgeInsets(),
        completionHandler = { cont.resume(Unit) },
      )
    }

  actual fun positionFromScreenLocation(xy: XY): Position =
    mapView.convertPoint(point = xy.toCGPoint(), toCoordinateFromView = null).toPosition()

  actual fun screenLocationFromPosition(position: Position): XY =
    mapView.convertCoordinate(position.toCLLocationCoordinate2D(), toPointToView = null).toXY()

  actual fun queryRenderedFeatures(xy: XY, layerIds: Set<String>): List<Feature> =
    mapView.visibleFeaturesAtPoint(CGPointMake(xy.x.toDouble(), xy.y.toDouble()), layerIds).map {
      (it as MLNFeatureProtocol).toFeature()
    }
}
