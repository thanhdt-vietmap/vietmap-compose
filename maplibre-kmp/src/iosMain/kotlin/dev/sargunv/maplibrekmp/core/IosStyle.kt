package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNSource
import cocoapods.MapLibre.MLNStyle
import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.layer.UnspecifiedLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.source.UnspecifiedSource

internal class IosStyle(style: MLNStyle) : Style {
  private var impl: MLNStyle = style

  override fun getSources(): List<Source> {
    return impl.sources.map { UnspecifiedSource(it as MLNSource) }
  }

  override fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  override fun removeSource(source: Source) {
    impl.removeSource(source.impl)
  }

  override fun getLayer(id: String): UnspecifiedLayer? {
    return impl.layerWithIdentifier(id)?.let { UnspecifiedLayer(it) }
  }

  override fun getLayers(): List<UnspecifiedLayer> {
    return impl.layers.map { UnspecifiedLayer(it as MLNStyleLayer) }
  }

  override fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  override fun addLayerAbove(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, aboveLayer = impl.layerWithIdentifier(id)!!)
  }

  override fun addLayerBelow(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, belowLayer = impl.layerWithIdentifier(id)!!)
  }

  override fun addLayerAt(index: Int, layer: Layer) {
    impl.insertLayer(layer.impl, atIndex = index.toULong())
  }

  override fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.impl)
  }
}
