package dev.sargunv.maplibrekmp.internal.compose

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.internal.wrapper.Style
import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.layer.Anchor

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

internal class StyleNode(style: Style) : MapNode() {

  internal val styleManager = StyleManager(style)

  override fun allowsChild(node: MapNode) = node is LayerNode<*>

  override fun onChildRemoved(oldIndex: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.removeLayer(layerNode.layer, layerNode.anchor, oldIndex)
  }

  override fun onChildInserted(index: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.addLayer(layerNode.layer, layerNode.anchor, index)
  }

  override fun onChildMoved(oldIndex: Int, index: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.removeLayer(layerNode.layer, layerNode.anchor, oldIndex)
    styleManager.addLayer(layerNode.layer, layerNode.anchor, index)
  }
}
