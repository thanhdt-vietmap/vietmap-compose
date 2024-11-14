package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import dev.sargunv.maplibrekmp.core.LatLng
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIGestureRecognizerStateBegan
import platform.UIKit.UIGestureRecognizerStateEnded
import platform.UIKit.UILongPressGestureRecognizer
import platform.UIKit.UITapGestureRecognizer
import platform.darwin.NSObject
import platform.darwin.sel_registerName

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  uiPadding: PaddingValues,
  styleUrl: String,
  updateMap: (map: PlatformMap) -> Unit,
  onMapLoaded: (map: PlatformMap) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onRelease: () -> Unit,
  onCameraMove: () -> Unit,
  onClick: (pos: LatLng) -> Unit,
  onLongClick: (pos: LatLng) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  val currentOnStyleLoaded by rememberUpdatedState(onStyleLoaded)
  val currentOnRelease by rememberUpdatedState(onRelease)
  val currentOnCameraMove by rememberUpdatedState(onCameraMove)
  val currentOnClick by rememberUpdatedState(onClick)
  val currentOnLongClick by rememberUpdatedState(onLongClick)

  // We shouldn't need to hold on to this, but for some reason it stops working if we don't.
  // Probably some weirdness with integration between Kotlin GC and ObjC ARC.
  var gestureManager by remember { mutableStateOf<MapGestureManager?>(null) }

  UIKitView(
    modifier = modifier.fillMaxSize(),
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = {
      MLNMapView().also { mapView ->
        mapView.automaticallyAdjustsContentInset = false

        onMapLoaded(PlatformMap(mapView))

        mapView.delegate =
          MapDelegate(
            onStyleLoaded = { currentOnStyleLoaded(Style(it)) },
            onCameraMove = { currentOnCameraMove() },
          )

        gestureManager =
          MapGestureManager(
            mapView = mapView,
            onClick = { currentOnClick(it) },
            onLongClick = { currentOnLongClick(it) },
          )
      }
    },
    update = { mapView ->
      val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
      val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
      val bottomSafeInset = insetPadding.calculateBottomPadding().value
      val leftUiPadding = uiPadding.calculateLeftPadding(layoutDir).value - leftSafeInset
      val rightUiPadding = uiPadding.calculateRightPadding(layoutDir).value - rightSafeInset
      val bottomUiPadding = uiPadding.calculateBottomPadding().value - bottomSafeInset
      mapView.setLogoViewMargins(CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble()))
      mapView.setAttributionButtonMargins(
        CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble())
      )
      mapView.setStyleURL(NSURL(string = styleUrl))
      updateMap(PlatformMap(mapView))
    },
    onRelease = {
      gestureManager = null
      currentOnRelease()
    },
  )
}

internal class MapDelegate(
  private val onStyleLoaded: (style: MLNStyle) -> Unit,
  private val onCameraMove: () -> Unit,
) : NSObject(), MLNMapViewDelegateProtocol {
  override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) =
    onStyleLoaded(didFinishLoadingStyle)

  override fun mapViewRegionIsChanging(mapView: MLNMapView) = onCameraMove()
}

@OptIn(BetaInteropApi::class)
internal class MapGestureManager(
  private val mapView: MLNMapView,
  private val onClick: (pos: LatLng) -> Unit,
  private val onLongClick: (pos: LatLng) -> Unit,
) : NSObject() {
  init {
    val singleTap = UITapGestureRecognizer(this, sel_registerName(::handleTap.name + ":"))
    val longPress =
      UILongPressGestureRecognizer(this, sel_registerName(::handleLongPress.name + ":"))

    mapView.gestureRecognizers!!
      .filterIsInstance<UITapGestureRecognizer>()
      .forEach(singleTap::requireGestureRecognizerToFail)
    mapView.gestureRecognizers!!
      .filterIsInstance<UILongPressGestureRecognizer>()
      .forEach(longPress::requireGestureRecognizerToFail)

    listOf(singleTap, longPress).forEach(mapView::addGestureRecognizer)
  }

  @ObjCAction
  fun handleTap(sender: UITapGestureRecognizer) {
    if (sender.state != UIGestureRecognizerStateEnded) return
    onClick(
      mapView
        .convertPoint(sender.locationInView(mapView), toCoordinateFromView = null)
        .useContents { LatLng(latitude, longitude) }
    )
  }

  @ObjCAction
  fun handleLongPress(sender: UILongPressGestureRecognizer) {
    if (sender.state != UIGestureRecognizerStateBegan) return
    onLongClick(
      mapView
        .convertPoint(sender.locationInView(mapView), toCoordinateFromView = null)
        .useContents { LatLng(latitude, longitude) }
    )
  }
}
