package dev.sargunv.maplibrekmp.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection
import cocoapods.MapLibre.*
import dev.sargunv.maplibrekmp.style.Layer
import dev.sargunv.maplibrekmp.style.Source
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
  options.block(StyleScope.Default(NativeStyleManager(this)))
}

internal class NativeStyleManager(private val style: MLNStyle) : StyleManager() {
  private fun getSource(id: String) =
    style.sourceWithIdentifier(id) ?: error("Source not found: $id")

  private fun getLayer(id: String) = style.layerWithIdentifier(id) ?: error("Layer not found: $id")

  override fun registerSource(source: Source) = style.addSource(source.toNativeSource())

  override fun registerLayer(layer: Layer) = style.addLayer(layer.toNativeLayer(::getSource))

  override fun registerLayerAbove(id: String, layer: Layer) =
    style.insertLayer(layer = layer.toNativeLayer(::getSource), aboveLayer = getLayer(id))

  override fun registerLayerBelow(id: String, layer: Layer) =
    style.insertLayer(layer = layer.toNativeLayer(::getSource), belowLayer = getLayer(id))

  override fun registerLayerAt(index: Int, layer: Layer) =
    style.insertLayer(layer = layer.toNativeLayer(::getSource), atIndex = index.toULong())

  override fun hasSource(id: String) = style.sourceWithIdentifier(id) != null

  override fun hasLayer(id: String) = style.layerWithIdentifier(id) != null
}
