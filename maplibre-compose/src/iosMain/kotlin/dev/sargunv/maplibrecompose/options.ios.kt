package dev.sargunv.maplibrecompose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import cocoapods.MapLibre.*
import platform.CoreGraphics.CGPointMake

internal fun MLNMapView.applyUiOptions(
  options: MaplibreMapOptions.UiOptions,
  insetPadding: PaddingValues,
  layoutDir: LayoutDirection,
) {
  logoView.setHidden(!options.isLogoEnabled)
  attributionButton.setHidden(!options.isAttributionEnabled)
  compassView.setHidden(!options.isCompassEnabled)

  debugMask = MLNMapDebugTileBoundariesMask or MLNMapDebugTileInfoMask

  allowsTilting = options.isTiltGesturesEnabled
  zoomEnabled = options.isZoomGesturesEnabled
  rotateEnabled = options.isRotateGesturesEnabled
  scrollEnabled = options.isScrollGesturesEnabled

  val leftSafeInset = insetPadding.calculateLeftPadding(layoutDir).value
  val rightSafeInset = insetPadding.calculateRightPadding(layoutDir).value
  val bottomSafeInset = insetPadding.calculateBottomPadding().value

  val leftUiPadding = options.padding.calculateLeftPadding(layoutDir).value - leftSafeInset
  val rightUiPadding = options.padding.calculateRightPadding(layoutDir).value - rightSafeInset
  val bottomUiPadding = options.padding.calculateBottomPadding().value - bottomSafeInset

  setLogoViewMargins(CGPointMake(leftUiPadding.toDouble(), bottomUiPadding.toDouble()))
  setAttributionButtonMargins(CGPointMake(rightUiPadding.toDouble(), bottomUiPadding.toDouble()))
}

internal fun MLNStyle.applyStyleOptions(options: MaplibreMapOptions.StyleOptions) {
  val getSource = { id: String -> sourceWithIdentifier(id) ?: error("Source not found: $id") }

  options.sources
    .map { (id, source) ->
      when (source) {
        is Source.GeoJson -> source.toNativeSource(id)
      }
    }
    .forEach { addSource(it) }

  options.layers
    .associateBy({ it }) { layer ->
      when (layer.type) {
        is Layer.Type.Line -> layer.type.toNativeLayer(getSource, layer)
      }
    }
    .forEach { (layer, nativeLayer) ->
      when {
        layer.below != null ->
          insertLayer(layer = nativeLayer, belowLayer = layerWithIdentifier(layer.below)!!)

        layer.above != null ->
          insertLayer(layer = nativeLayer, aboveLayer = layerWithIdentifier(layer.above)!!)

        layer.index != null -> insertLayer(layer = nativeLayer, atIndex = layer.index.toULong())

        else -> addLayer(nativeLayer)
      }
    }
}
