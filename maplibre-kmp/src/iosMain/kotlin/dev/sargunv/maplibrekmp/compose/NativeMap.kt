package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import dev.sargunv.maplibrekmp.core.MapControl
import dev.sargunv.maplibrekmp.core.Style
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.darwin.NSObject

@Composable
internal actual fun NativeMap(
  modifier: Modifier,
  uiPadding: PaddingValues,
  styleUrl: String,
  updateMap: (map: MapControl) -> Unit,
  onStyleLoaded: (style: Style) -> Unit,
  onRelease: () -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()
  val currentOnStyleLoaded by rememberUpdatedState(onStyleLoaded)

  UIKitView(
    modifier = modifier.fillMaxSize(),
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = {
      MLNMapView().also {
        it.delegate =
          object : NSObject(), MLNMapViewDelegateProtocol {
            override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
              currentOnStyleLoaded(Style(didFinishLoadingStyle))
            }
          }
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

      updateMap(MapControl(mapView))
      mapView.setStyleURL(NSURL(string = styleUrl))
    },
    onRelease = { onRelease() },
  )
}
