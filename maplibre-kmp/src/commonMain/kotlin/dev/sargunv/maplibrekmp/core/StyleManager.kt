package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

@Stable
internal class StyleManager(val style: Style) {

  private val sources =
    mutableStateMapOf<String, Source>().apply { style.getSources().forEach { put(it.id, it) } }

  private val layers =
    mutableStateMapOf<String, Layer>().apply { style.getLayers().forEach { put(it.id, it) } }

  private val layersByAnchor = mutableMapOf<Anchor, MutableList<Pair<Int, Layer>>>()

  internal fun addSource(source: Source) {
    require(source.id !in sources) { "Source with ID ${source.id} already exists" }
    sources[source.id] = source
    style.addSource(source)
  }

  internal fun removeSource(source: Source) {
    require(source.id in sources) { "Source with ID ${source.id} does not exist" }
    sources.remove(source.id)
    style.removeSource(source)
  }

  internal fun addLayer(layer: Layer, anchor: Anchor, sortKey: Int) {
    require(layer.id !in layers) { "Layer with ID ${layer.id} already exists" }
    layers[layer.id] = layer
    if (anchor !in layersByAnchor) {
      when (anchor) {
        Anchor.Top -> style.addLayer(layer)
        Anchor.Bottom -> style.addLayerAt(0, layer)
        is Anchor.Above -> style.addLayerAbove(anchor.layerId, layer)
        is Anchor.Below -> style.addLayerBelow(anchor.layerId, layer)
      }
      layersByAnchor[anchor] = mutableListOf(sortKey to layer)
    } else {
      layersByAnchor[anchor]!!.let { anchoredLayers ->
        anchoredLayers
          .binarySearch { it.first.compareTo(sortKey) }
          .let { invIndex ->
            require(invIndex < 0) { "Layer with sort key $sortKey already exists" }
            val i = invIndex.inv()
            if (i == anchoredLayers.size) {
              style.addLayerAbove(anchoredLayers.last().second.id, layer)
              anchoredLayers.add(sortKey to layer)
            } else {
              style.addLayerBelow(anchoredLayers[i].second.id, layer)
              anchoredLayers.add(i, sortKey to layer)
            }
          }
      }
    }
  }

  internal fun removeLayer(layer: Layer, anchor: Anchor, sortKey: Int) {
    require(layer.id in layers) { "Layer with ID ${layer.id} does not exist" }
    layers.remove(layer.id)
    style.removeLayer(layer)
    val anchoredLayers = layersByAnchor[anchor]!!
    anchoredLayers.remove(sortKey to layer)
    if (anchoredLayers.isEmpty()) {
      layersByAnchor.remove(anchor)
    }
  }
}
