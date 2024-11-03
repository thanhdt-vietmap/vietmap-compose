package dev.sargunv.maplibrecompose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import platform.Foundation.NSURL
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.darwin.NSObject

@Composable
public actual fun MaplibreMap(modifier: Modifier, options: MaplibreMapOptions) {
  val layoutDir = LocalLayoutDirection.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  UIKitView(
    modifier = modifier.fillMaxSize(),
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = { MLNMapView() },
    update = { mapView ->
      mapView.apply {
        autoresizingMask = UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
        delegate =
          object : NSObject(), MLNMapViewDelegateProtocol {
            override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
              didFinishLoadingStyle.applyStyleOptions(options.style)
            }
          }
        applyUiOptions(options.ui, insetPadding, layoutDir)
        setStyleURL(NSURL(string = options.style.url))
      }
    },
  )
}
