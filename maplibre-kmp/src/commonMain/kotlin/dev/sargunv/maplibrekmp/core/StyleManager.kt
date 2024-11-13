package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

@Stable
internal class StyleManager(val style: Style) {

  private val sources =
    mutableStateMapOf<String, Source>().apply { style.getSources().forEach { put(it.id, it) } }

  private val layers =
    mutableStateMapOf<String, Layer>().apply { style.getLayers().forEach { put(it.id, it) } }

  private val layerAnchorList = mutableListOf<Pair<Anchor, Layer>>()

  internal fun addSource(source: Source) {
    println("Adding source ${source.id}")
    require(source.id !in sources) { "Source with ID ${source.id} already exists" }
    sources[source.id] = source
    style.addSource(source)
  }

  internal fun removeSource(source: Source) {
    println("Removing source ${source.id}")
    require(source.id in sources) { "Source with ID ${source.id} does not exist" }
    sources.remove(source.id)
    style.removeSource(source)
  }

  internal fun addLayer(layer: Layer, anchor: Anchor, index: Int) {
    println("Adding layer ${layer.id} with anchor $anchor and index $index")
    require(layer.id !in layers) { "Layer with ID ${layer.id} already exists" }

    layerAnchorList.add(index, anchor to layer)
    layers[layer.id] = layer

    val nextLayer = nextLayerIdForAnchor(anchor, index)
    if (nextLayer != null) style.addLayerBelow(nextLayer, layer)
    else {
      val prevLayer = prevLayerIdForAnchor(anchor, index)
      if (prevLayer != null) {
        style.addLayerAbove(prevLayer, layer)
      } else {
        initAnchor(anchor, layer)
      }
    }
  }

  internal fun removeLayer(layer: Layer, anchor: Anchor, index: Int) {
    println("Removing layer ${layer.id} with anchor $anchor and sort key $index")
    require(layer.id in layers) { "Layer with ID ${layer.id} does not exist" }
    layerAnchorList.removeAt(index)
    layers.remove(layer.id)
    style.removeLayer(layer)
  }

  internal fun moveLayer(layer: Layer, anchor: Anchor, oldIndex: Int, index: Int) {
    println("Moving layer ${layer.id} with anchor $anchor from $oldIndex to $index")
    require(layer.id in layers) { "Layer with ID ${layer.id} does not exist" }

    if (oldIndex < index) {
      val oldNextLayer = nextLayerIdForAnchor(anchor, oldIndex)
      layerAnchorList.removeAt(oldIndex)
      layerAnchorList.add(index, anchor to layer)
      val newNextLayer = nextLayerIdForAnchor(anchor, index)
      if (oldNextLayer != newNextLayer) {
        println("Position changed relative to other layers on the same anchor")
        style.removeLayer(layer)
        if (newNextLayer != null) style.addLayerBelow(newNextLayer, layer)
        else {
          val prevLayer = prevLayerIdForAnchor(anchor, index)
          if (prevLayer != null) style.addLayerAbove(prevLayer, layer)
          else initAnchor(anchor, layer)
        }
      }
    } else {
      val oldPrevLayer = prevLayerIdForAnchor(anchor, oldIndex)
      layerAnchorList.removeAt(oldIndex)
      layerAnchorList.add(index, anchor to layer)
      val newPrevLayer = prevLayerIdForAnchor(anchor, index)
      if (oldPrevLayer != newPrevLayer) {
        println("Position changed relative to other layers on the same anchor")
        style.removeLayer(layer)
        if (newPrevLayer != null) style.addLayerAbove(newPrevLayer, layer)
        else {
          val nextLayer = nextLayerIdForAnchor(anchor, index)
          if (nextLayer != null) style.addLayerBelow(nextLayer, layer)
          else initAnchor(anchor, layer)
        }
      }
    }
  }

  private fun initAnchor(anchor: Anchor, layer: Layer) {
    when (anchor) {
      Anchor.Top -> style.addLayer(layer)
      Anchor.Bottom -> style.addLayerAt(0, layer)
      is Anchor.Above -> style.addLayerAbove(anchor.layerId, layer)
      is Anchor.Below -> style.addLayerBelow(anchor.layerId, layer)
    }
  }

  private fun nextLayerIdForAnchor(anchor: Anchor, index: Int) =
    layerAnchorList
      .subList(index, layerAnchorList.size)
      .firstOrNull { it.first == anchor }
      ?.second
      ?.id

  private fun prevLayerIdForAnchor(anchor: Anchor, index: Int) =
    layerAnchorList.subList(0, index).lastOrNull { it.first == anchor }?.second?.id
}
