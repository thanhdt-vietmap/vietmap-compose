package dev.sargunv.maplibrekmp.map

import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.Layer
import org.maplibre.android.style.sources.Source

internal class NativeStyleManager(private val style: Style) :
  StyleManager<Source, Layer>(NativeSourceAdapter, NativeLayerAdapter) {

  override fun registerSource(source: Source) = style.addSource(source)

  override fun registerLayer(layer: Layer) = style.addLayer(layer)

  override fun registerLayerAbove(id: String, layer: Layer) = style.addLayerAbove(layer, id)

  override fun registerLayerBelow(id: String, layer: Layer) = style.addLayerBelow(layer, id)

  override fun registerLayerAt(index: Int, layer: Layer) = style.addLayerAt(layer, index)

  override fun hasSource(id: String) = style.getSource(id) != null

  override fun hasLayer(id: String) = style.getLayer(id) != null
}
