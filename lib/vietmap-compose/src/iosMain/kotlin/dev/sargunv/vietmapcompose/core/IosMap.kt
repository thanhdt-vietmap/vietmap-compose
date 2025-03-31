package dev.sargunv.vietmapcompose.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import cocoapods.VietMap.MLNAltitudeForZoomLevel
import cocoapods.VietMap.MLNCameraChangeReason
import cocoapods.VietMap.MLNCameraChangeReasonGestureOneFingerZoom
import cocoapods.VietMap.MLNCameraChangeReasonGesturePan
import cocoapods.VietMap.MLNCameraChangeReasonGesturePinch
import cocoapods.VietMap.MLNCameraChangeReasonGestureRotate
import cocoapods.VietMap.MLNCameraChangeReasonGestureTilt
import cocoapods.VietMap.MLNCameraChangeReasonGestureZoomIn
import cocoapods.VietMap.MLNCameraChangeReasonGestureZoomOut
import cocoapods.VietMap.MLNCameraChangeReasonProgrammatic
import cocoapods.VietMap.MLNFeatureProtocol
import cocoapods.VietMap.MLNLoggingBlockHandler
import cocoapods.VietMap.MLNLoggingConfiguration
import cocoapods.VietMap.MLNLoggingLevel
import cocoapods.VietMap.MLNLoggingLevelDebug
import cocoapods.VietMap.MLNLoggingLevelError
import cocoapods.VietMap.MLNLoggingLevelFault
import cocoapods.VietMap.MLNLoggingLevelInfo
import cocoapods.VietMap.MLNLoggingLevelVerbose
import cocoapods.VietMap.MLNLoggingLevelWarning
import cocoapods.VietMap.MLNMapCamera
import cocoapods.VietMap.MLNMapDebugCollisionBoxesMask
import cocoapods.VietMap.MLNMapDebugTileBoundariesMask
import cocoapods.VietMap.MLNMapDebugTileInfoMask
import cocoapods.VietMap.MLNMapDebugTimestampsMask
import cocoapods.VietMap.MLNMapView
import cocoapods.VietMap.MLNMapViewDelegateProtocol
import cocoapods.VietMap.MLNOrnamentPosition
import cocoapods.VietMap.MLNOrnamentPositionBottomLeft
import cocoapods.VietMap.MLNOrnamentPositionBottomRight
import cocoapods.VietMap.MLNOrnamentPositionTopLeft
import cocoapods.VietMap.MLNOrnamentPositionTopRight
import cocoapods.VietMap.MLNStyle
import cocoapods.VietMap.MLNZoomLevelForAltitude
import cocoapods.VietMap.allowsTilting
import dev.sargunv.VietMapcompose.core.util.toBoundingBox
import dev.sargunv.VietMapcompose.core.util.toCGPoint
import dev.sargunv.VietMapcompose.core.util.toCGRect
import dev.sargunv.VietMapcompose.core.util.toCLLocationCoordinate2D
import dev.sargunv.VietMapcompose.core.util.toDpOffset
import dev.sargunv.VietMapcompose.core.util.toFeature
import dev.sargunv.VietMapcompose.core.util.toMLNCoordinateBounds
import dev.sargunv.VietMapcompose.core.util.toMLNOrnamentPosition
import dev.sargunv.VietMapcompose.core.util.toNSPredicate
import dev.sargunv.VietMapcompose.core.util.toPosition
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.BoundingBox
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.Position
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.TimeSource
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGSize
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.UIKit.UIEdgeInsets
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UIGestureRecognizer
import platform.UIKit.UIGestureRecognizerStateBegan
import platform.UIKit.UIGestureRecognizerStateEnded
import platform.UIKit.UILongPressGestureRecognizer
import platform.UIKit.UITapGestureRecognizer
import platform.darwin.NSObject
import platform.darwin.NSUInteger
import platform.darwin.sel_registerName

internal class IosMap(
  private var mapView: MLNMapView,
  internal var size: CValue<CGSize>,
  internal var layoutDir: LayoutDirection,
  internal var density: Density,
  internal var insetPadding: PaddingValues,
  internal var callbacks: MaplibreMap.Callbacks,
  internal var logger: Logger?,
) : StandardMaplibreMap {

  // hold strong references to things that the sdk keeps weak references to
  private val gestures = mutableListOf<Gesture<*>>()
  private val delegate: Delegate

  init {
    mapView.automaticallyAdjustsContentInset = false

    addGestures(
      Gesture(UITapGestureRecognizer()) {
        if (state != UIGestureRecognizerStateEnded) return@Gesture
        val point = locationInView(this@IosMap.mapView).toDpOffset()
        callbacks.onClick(this@IosMap, positionFromScreenLocation(point), point)
      },
      Gesture(UILongPressGestureRecognizer()) {
        if (state != UIGestureRecognizerStateBegan) return@Gesture
        val point = locationInView(this@IosMap.mapView).toDpOffset()
        callbacks.onLongClick(this@IosMap, positionFromScreenLocation(point), point)
      },
    )

    // delegate log level configuration to Kermit logger
    MLNLoggingConfiguration.sharedConfiguration.setHandler(LoggingBlockHandler(this))
    MLNLoggingConfiguration.sharedConfiguration.loggingLevel = MLNLoggingLevelVerbose

    delegate = Delegate(this)
    mapView.delegate = delegate
  }

  private class LoggingBlockHandler(private val map: IosMap) : MLNLoggingBlockHandler {
    override fun invoke(level: MLNLoggingLevel, path: String?, line: NSUInteger, message: String?) {
      when (level) {
        MLNLoggingLevelFault -> map.logger?.a { "$message" }
        MLNLoggingLevelError -> map.logger?.e { "$message" }
        MLNLoggingLevelWarning -> map.logger?.w { "$message" }
        MLNLoggingLevelInfo -> map.logger?.i { "$message" }
        MLNLoggingLevelDebug -> map.logger?.d { "$message" }
        MLNLoggingLevelVerbose -> map.logger?.v { "$message" }
        else -> error("Unexpected logging level: $level")
      }
    }
  }

  private class Delegate(private val map: IosMap) : NSObject(), MLNMapViewDelegateProtocol {

    val timeSource = TimeSource.Monotonic
    var lastFrameTime = timeSource.markNow()

    override fun mapViewWillStartLoadingMap(mapView: MLNMapView) {
      map.logger?.i { "Map will start loading" }
    }

    override fun mapViewDidFailLoadingMap(mapView: MLNMapView, withError: NSError) {
      map.logger?.e { "Map failed to load: $withError" }
    }

    override fun mapViewDidFinishLoadingMap(mapView: MLNMapView) {
      map.logger?.i { "Map finished loading" }
    }

    override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
      map.logger?.i { "Style finished loading" }
      map.callbacks.onStyleChanged(
        map = map,
        style = IosStyle(style = didFinishLoadingStyle, getScale = { map.density.density }),
      )
    }

    private val anyGesture =
      (MLNCameraChangeReasonGestureOneFingerZoom or
        MLNCameraChangeReasonGesturePan or
        MLNCameraChangeReasonGesturePinch or
        MLNCameraChangeReasonGestureRotate or
        MLNCameraChangeReasonGestureTilt or
        MLNCameraChangeReasonGestureZoomIn or
        MLNCameraChangeReasonGestureZoomOut)

    @ObjCSignatureOverride
    override fun mapView(
      mapView: MLNMapView,
      @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
      regionWillChangeWithReason: MLNCameraChangeReason,
      animated: Boolean,
    ) {
      map.callbacks.onCameraMoveStarted(
        map,
        if (regionWillChangeWithReason and anyGesture != 0uL) {
          CameraMoveReason.GESTURE
        } else if (regionWillChangeWithReason and MLNCameraChangeReasonProgrammatic != 0uL) {
          CameraMoveReason.PROGRAMMATIC
        } else {
          map.logger?.w { "Unknown camera move reason: $regionWillChangeWithReason" }
          CameraMoveReason.UNKNOWN
        },
      )
    }

    override fun mapViewRegionIsChanging(mapView: MLNMapView) {
      map.callbacks.onCameraMoved(map)
    }

    @ObjCSignatureOverride
    override fun mapView(
      mapView: MLNMapView,
      @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
      regionDidChangeWithReason: MLNCameraChangeReason,
      animated: Boolean,
    ) {
      map.callbacks.onCameraMoveEnded(map)
    }

    override fun mapViewDidFinishRenderingFrame(mapView: MLNMapView, fullyRendered: Boolean) {
      val time = timeSource.markNow()
      val duration = time - lastFrameTime
      lastFrameTime = time
      map.callbacks.onFrame(1.0 / duration.toDouble(DurationUnit.SECONDS))
    }
  }

  private var lastStyleUri: String = ""

  override fun setStyleUri(styleUri: String) {
    if (styleUri == lastStyleUri) return
    lastStyleUri = styleUri
    logger?.i { "Setting style URI" }
    callbacks.onStyleChanged(this, null)
    mapView.setStyleURL(NSURL(string = styleUri))
  }

  internal class Gesture<T : UIGestureRecognizer>(
    val recognizer: T,
    val isCooperative: Boolean = true,
    private val action: T.() -> Unit,
  ) : NSObject() {
    init {
      recognizer.addTarget(target = this, action = sel_registerName(::handleGesture.name + ":"))
    }

    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun handleGesture(sender: UIGestureRecognizer) {
      @Suppress("UNCHECKED_CAST") action(sender as T)
    }
  }

  private inline fun <reified T : UIGestureRecognizer> addGestures(vararg gestures: Gesture<T>) {
    gestures.forEach { gesture ->
      if (gesture.isCooperative) {
        mapView.gestureRecognizers!!.filterIsInstance<T>().forEach {
          gesture.recognizer.requireGestureRecognizerToFail(it)
        }
      }
      this.gestures.add(gesture)
      mapView.addGestureRecognizer(gesture.recognizer)
    }
  }

  override fun setDebugEnabled(enabled: Boolean) {
    mapView.debugMask =
      if (enabled)
        MLNMapDebugTileBoundariesMask or
          MLNMapDebugTileInfoMask or
          MLNMapDebugTimestampsMask or
          MLNMapDebugCollisionBoxesMask
      else 0uL
  }

  override fun setMinPitch(minPitch: Double) {
    mapView.minimumPitch = minPitch
  }

  override fun setMaxPitch(maxPitch: Double) {
    mapView.maximumPitch = maxPitch
  }

  override fun setMinZoom(minZoom: Double) {
    mapView.minimumZoomLevel = minZoom
  }

  override fun setMaxZoom(maxZoom: Double) {
    mapView.maximumZoomLevel = maxZoom
  }

  override fun getVisibleBoundingBox(): BoundingBox {
    return mapView.visibleCoordinateBounds.toBoundingBox()
  }

  override fun getVisibleRegion(): VisibleRegion {
    return size.useContents {
      VisibleRegion(
        farLeft = positionFromScreenLocation(DpOffset(x = 0.dp, y = 0.dp)),
        farRight = positionFromScreenLocation(DpOffset(x = width.dp, y = 0.dp)),
        nearLeft = positionFromScreenLocation(DpOffset(x = 0.dp, y = height.dp)),
        nearRight = positionFromScreenLocation(DpOffset(x = width.dp, y = height.dp)),
      )
    }
  }

  override fun setMaximumFps(maximumFps: Int) {
    mapView.preferredFramesPerSecond = maximumFps.toLong()
  }

  override fun setGestureSettings(value: GestureSettings) {
    mapView.rotateEnabled = value.isRotateGesturesEnabled
    mapView.scrollEnabled = value.isScrollGesturesEnabled
    mapView.allowsTilting = value.isTiltGesturesEnabled
    mapView.zoomEnabled = value.isZoomGesturesEnabled
  }

  private fun calculateMargins(
    ornamentPosition: MLNOrnamentPosition,
    uiPadding: PaddingValues,
  ): CValue<CGPoint> {
    return when (ornamentPosition) {
      MLNOrnamentPositionTopLeft ->
        CGPointMake(
          (uiPadding.calculateLeftPadding(layoutDir).value -
              insetPadding.calculateLeftPadding(layoutDir).value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
          (uiPadding.calculateTopPadding().value - insetPadding.calculateTopPadding().value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
        )

      MLNOrnamentPositionTopRight ->
        CGPointMake(
          (uiPadding.calculateRightPadding(layoutDir).value -
              insetPadding.calculateRightPadding(layoutDir).value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
          (uiPadding.calculateTopPadding().value - insetPadding.calculateTopPadding().value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
        )

      MLNOrnamentPositionBottomLeft ->
        CGPointMake(
          (uiPadding.calculateLeftPadding(layoutDir).value -
              insetPadding.calculateLeftPadding(layoutDir).value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
          (uiPadding.calculateBottomPadding().value - insetPadding.calculateBottomPadding().value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
        )

      MLNOrnamentPositionBottomRight ->
        CGPointMake(
          (uiPadding.calculateRightPadding(layoutDir).value -
              insetPadding.calculateRightPadding(layoutDir).value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
          (uiPadding.calculateBottomPadding().value - insetPadding.calculateBottomPadding().value)
            .toDouble()
            .coerceAtLeast(0.0) + 8.0,
        )

      else -> error("Invalid ornament position")
    }
  }

  override fun setOrnamentSettings(value: OrnamentSettings) {
    mapView.logoView.hidden = !value.isLogoEnabled
    mapView.logoViewPosition = value.logoAlignment.toMLNOrnamentPosition(layoutDir)
    mapView.logoViewMargins = calculateMargins(mapView.logoViewPosition, value.padding)

    mapView.attributionButton.hidden = !value.isAttributionEnabled
    mapView.attributionButtonPosition = value.attributionAlignment.toMLNOrnamentPosition(layoutDir)
    mapView.attributionButtonMargins =
      calculateMargins(mapView.attributionButtonPosition, value.padding)

    mapView.compassView.hidden = !value.isCompassEnabled
    mapView.compassViewPosition = value.compassAlignment.toMLNOrnamentPosition(layoutDir)
    mapView.compassViewMargins = calculateMargins(mapView.compassViewPosition, value.padding)

    mapView.scaleBar.hidden = !value.isScaleBarEnabled
    mapView.scaleBarPosition = value.scaleBarAlignment.toMLNOrnamentPosition(layoutDir)
    mapView.scaleBarMargins = calculateMargins(mapView.scaleBarPosition, value.padding)
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

  override fun getCameraPosition(): CameraPosition {
    return mapView.camera.toCameraPosition(
      paddingValues =
        mapView.cameraEdgeInsets.useContents {
          PaddingValues.Absolute(left = left.dp, top = top.dp, right = right.dp, bottom = bottom.dp)
        }
    )
  }

  override fun setCameraPosition(cameraPosition: CameraPosition) {
    mapView.setCamera(
      cameraPosition.toMLNMapCamera(),
      withDuration = 0.0,
      animationTimingFunction = null,
      edgePadding = cameraPosition.padding.toEdgeInsets(),
      completionHandler = null,
    )
  }

  private fun PaddingValues.toEdgeInsets(): CValue<UIEdgeInsets> =
    UIEdgeInsetsMake(
      top = calculateTopPadding().value.toDouble(),
      left = calculateLeftPadding(layoutDir).value.toDouble(),
      bottom = calculateBottomPadding().value.toDouble(),
      right = calculateRightPadding(layoutDir).value.toDouble(),
    )

  override suspend fun animateCameraPosition(finalPosition: CameraPosition, duration: Duration) =
    suspendCoroutine { cont ->
      mapView.flyToCamera(
        camera = finalPosition.toMLNMapCamera(),
        withDuration = duration.toDouble(DurationUnit.SECONDS),
        edgePadding = finalPosition.padding.toEdgeInsets(),
        completionHandler = { cont.resume(Unit) },
      )
    }

  override suspend fun animateCameraPosition(
    boundingBox: BoundingBox,
    bearing: Double,
    tilt: Double,
    padding: PaddingValues,
    duration: Duration,
  ) {
    suspendCoroutine { cont ->
      mapView.flyToCamera(
        camera =
          mapView.cameraThatFitsCoordinateBounds(boundingBox.toMLNCoordinateBounds()).apply {
            heading = bearing
            pitch = tilt
          },
        withDuration = duration.toDouble(DurationUnit.SECONDS),
        edgePadding = padding.toEdgeInsets(),
        completionHandler = { cont.resume(Unit) },
      )
    }
  }

  override fun positionFromScreenLocation(offset: DpOffset): Position =
    mapView.convertPoint(point = offset.toCGPoint(), toCoordinateFromView = mapView).toPosition()

  override fun screenLocationFromPosition(position: Position): DpOffset =
    mapView
      .convertCoordinate(position.toCLLocationCoordinate2D(), toPointToView = mapView)
      .toDpOffset()

  override fun queryRenderedFeatures(
    offset: DpOffset,
    layerIds: Set<String>?,
    predicate: CompiledExpression<BooleanValue>?,
  ): List<Feature> =
    mapView
      .visibleFeaturesAtPoint(
        point = offset.toCGPoint(),
        inStyleLayersWithIdentifiers = layerIds,
        predicate = predicate?.toNSPredicate(),
      )
      .map { (it as MLNFeatureProtocol).toFeature() }

  override fun queryRenderedFeatures(
    rect: DpRect,
    layerIds: Set<String>?,
    predicate: CompiledExpression<BooleanValue>?,
  ): List<Feature> =
    mapView
      .visibleFeaturesInRect(
        rect = rect.toCGRect(),
        inStyleLayersWithIdentifiers = layerIds,
        predicate = predicate?.toNSPredicate(),
      )
      .map { (it as MLNFeatureProtocol).toFeature() }

  override fun metersPerDpAtLatitude(latitude: Double) = mapView.metersPerPointAtLatitude(latitude)
}
