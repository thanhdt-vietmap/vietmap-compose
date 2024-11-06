package dev.sargunv.maplibrekmp.map

import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer

internal class NativeStyleManager(private val style: MLNStyle) :
  StyleManager<MLNSource, MLNStyleLayer>(NativeSourceAdapter, NativeLayerAdapter(style)) {
  private fun getLayer(id: String) = style.layerWithIdentifier(id) ?: error("Layer not found: $id")

  override fun registerSource(source: MLNSource) = style.addSource(source)

  override fun registerLayer(layer: MLNStyleLayer) = style.addLayer(layer)

  override fun registerLayerAbove(id: String, layer: MLNStyleLayer) =
    style.insertLayer(layer = layer, aboveLayer = getLayer(id))

  override fun registerLayerBelow(id: String, layer: MLNStyleLayer) =
    style.insertLayer(layer = layer, belowLayer = getLayer(id))

  override fun registerLayerAt(index: Int, layer: MLNStyleLayer) =
    style.insertLayer(layer = layer, atIndex = index.toULong())

  override fun hasSource(id: String) = style.sourceWithIdentifier(id) != null

  override fun hasLayer(id: String) = style.layerWithIdentifier(id) != null
}
