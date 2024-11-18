package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.layer.UnspecifiedLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.source.UnspecifiedSource
import org.maplibre.android.maps.Style as MLNStyle

internal class AndroidStyle(style: MLNStyle) : Style {
  private var impl: MLNStyle = style

  override fun getSources(): List<Source> {
    return impl.sources.map { UnspecifiedSource(it) }
  }

  override fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  override fun removeSource(source: Source) {
    impl.removeSource(source.impl)
  }

  override fun getLayer(id: String): UnspecifiedLayer? {
    return impl.getLayer(id)?.let { UnspecifiedLayer(it) }
  }

  override fun getLayers(): List<UnspecifiedLayer> {
    return impl.layers.map { UnspecifiedLayer(it) }
  }

  override fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  override fun addLayerAbove(id: String, layer: Layer) {
    impl.addLayerAbove(layer.impl, id)
  }

  override fun addLayerBelow(id: String, layer: Layer) {
    impl.addLayerBelow(layer.impl, id)
  }

  override fun addLayerAt(index: Int, layer: Layer) {
    impl.addLayerAt(layer.impl, index)
  }

  override fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.impl)
  }
}
