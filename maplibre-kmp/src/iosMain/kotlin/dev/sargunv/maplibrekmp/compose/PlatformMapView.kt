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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import dev.sargunv.maplibrekmp.core.Gesture
import dev.sargunv.maplibrekmp.core.MapViewDelegate
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import dev.sargunv.maplibrekmp.core.toCGSize
import dev.sargunv.maplibrekmp.core.toXY
import io.github.dellisd.spatialk.geojson.Position
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIGestureRecognizerStateBegan
import platform.UIKit.UIGestureRecognizerStateEnded
import platform.UIKit.UILongPressGestureRecognizer
import platform.UIKit.UITapGestureRecognizer

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  uiPadding: PaddingValues,
  updateMap: (map: PlatformMap) -> Unit,
  onMapLoaded: (map: PlatformMap) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onReset: () -> Unit,
  onCameraMove: () -> Unit,
  onClick: (latLng: Position, xy: XY) -> Unit,
  onLongClick: (latLng: Position, xy: XY) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  val margins =
    remember(insetPadding, uiPadding, layoutDir) {
      val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
      val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
      val bottomSafeInset = insetPadding.calculateBottomPadding().value
      val leftUiPadding = uiPadding.calculateLeftPadding(layoutDir).value - leftSafeInset
      val rightUiPadding = uiPadding.calculateRightPadding(layoutDir).value - rightSafeInset
      val bottomUiPadding = uiPadding.calculateBottomPadding().value - bottomSafeInset
      listOf(
        CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble()),
        CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble()),
      )
    }

  var lastStyleUrl by remember { mutableStateOf<String?>(null) }

  val currentOnStyleLoaded by rememberUpdatedState(onStyleLoaded)
  val currentOnReset by rememberUpdatedState(onReset)
  val currentOnCameraMove by rememberUpdatedState(onCameraMove)
  val currentOnClick by rememberUpdatedState(onClick)
  val currentOnLongClick by rememberUpdatedState(onLongClick)

  var size by remember { mutableStateOf(DpSize.Unspecified) }
  var platformMap by remember { mutableStateOf<PlatformMap?>(null) }

  UIKitView(
    modifier =
      modifier.fillMaxSize().onSizeChanged { size = with(density) { it.toSize().toDpSize() } },
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = ::MLNMapView,
    update = { mapView ->
      mapView.automaticallyAdjustsContentInset = false

      if (platformMap == null && size.isSpecified) {
        val map = PlatformMap(mapView, size.toCGSize(), layoutDir)

        map.addGesture(
          Gesture(UITapGestureRecognizer()) {
            if (state != UIGestureRecognizerStateEnded) return@Gesture
            val point = locationInView(mapView).toXY()
            currentOnClick(map.positionFromScreenLocation(point), point)
          }
        )

        map.addGesture(
          Gesture(UILongPressGestureRecognizer()) {
            if (state != UIGestureRecognizerStateBegan) return@Gesture
            val point = locationInView(mapView).toXY()
            currentOnLongClick(map.positionFromScreenLocation(point), point)
          }
        )

        map.delegate =
          MapViewDelegate(
            onStyleLoaded = { currentOnStyleLoaded(Style(it)) },
            onCameraMove = { currentOnCameraMove() },
          )

        platformMap = map
        onMapLoaded(map)
      }

      platformMap?.let {
        if (size.isSpecified) it.size = size.toCGSize()
        it.layoutDir = layoutDir
        updateMap(it)
      }

      mapView.setLogoViewMargins(margins[0])
      mapView.setAttributionButtonMargins(margins[1])

      if (styleUrl != lastStyleUrl) {
        mapView.setStyleURL(NSURL(string = styleUrl))
        lastStyleUrl = styleUrl
      }
    },
    onReset = {
      platformMap = null
      currentOnReset()
    },
  )
}
