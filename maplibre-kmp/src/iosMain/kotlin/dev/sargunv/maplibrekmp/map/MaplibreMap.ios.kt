package dev.sargunv.maplibrekmp.map

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapDebugTileBoundariesMask
import cocoapods.MapLibre.MLNMapDebugTileInfoMask
import cocoapods.MapLibre.MLNMapView
import cocoapods.MapLibre.MLNMapViewDelegateProtocol
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer
import cocoapods.MapLibre.allowsTilting
import dev.sargunv.maplibrekmp.compose.MapNode
import dev.sargunv.maplibrekmp.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.StyleScope
import dev.sargunv.maplibrekmp.style.Style
import kotlinx.coroutines.awaitCancellation
import platform.CoreGraphics.CGPointMake
import platform.Foundation.NSURL
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.darwin.NSObject

@Composable
public actual fun MaplibreMap(
  modifier: Modifier,
  options: MaplibreMapOptions,
  styleContent: @Composable StyleScope.() -> Unit,
) {
  var style by remember { mutableStateOf<MLNStyle?>(null) }
  var mapFinishedLoading by remember { mutableStateOf(false) }
  val compositionContext = rememberCompositionContext()

  val layoutDir = LocalLayoutDirection.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  UIKitView(
    modifier = modifier.fillMaxSize(),
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = {
      MLNMapView().apply {
        delegate =
          object : NSObject(), MLNMapViewDelegateProtocol {
            override fun mapViewDidFinishLoadingMap(mapView: MLNMapView) {
              mapFinishedLoading = true
            }

            override fun mapView(mapView: MLNMapView, didFinishLoadingStyle: MLNStyle) {
              println(didFinishLoadingStyle.layers.map { (it as MLNStyleLayer).identifier })
              style = didFinishLoadingStyle
            }
          }
      }
    },
    update = { mapView ->
      mapView.apply {
        debugMask = MLNMapDebugTileBoundariesMask or MLNMapDebugTileInfoMask

        autoresizingMask = UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
        logoView.setHidden(!options.ui.isLogoEnabled)
        attributionButton.setHidden(!options.ui.isAttributionEnabled)
        compassView.setHidden(!options.ui.isCompassEnabled)

        allowsTilting = options.ui.isTiltGesturesEnabled
        zoomEnabled = options.ui.isZoomGesturesEnabled
        rotateEnabled = options.ui.isRotateGesturesEnabled
        scrollEnabled = options.ui.isScrollGesturesEnabled

        val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
        val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
        val bottomSafeInset = insetPadding.calculateBottomPadding().value
        val leftUiPadding = options.ui.padding.calculateLeftPadding(layoutDir).value - leftSafeInset
        val rightUiPadding =
          options.ui.padding.calculateRightPadding(layoutDir).value - rightSafeInset
        val bottomUiPadding = options.ui.padding.calculateBottomPadding().value - bottomSafeInset

        setLogoViewMargins(CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble()))
        setAttributionButtonMargins(
          CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble())
        )

        setStyleURL(NSURL(string = options.style.url))
      }
    },
  )

  LaunchedEffect(style, mapFinishedLoading) {
    if (mapFinishedLoading) {
      style?.let { style ->
        val composition =
          Composition(MapNodeApplier(MapNode.StyleNode(Style(style))), compositionContext)
        composition.setContent { StyleScope.styleContent() }
        try {
          awaitCancellation()
        } finally {
          composition.dispose()
        }
      }
    }
  }
}
