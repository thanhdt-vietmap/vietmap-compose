package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.UnspecifiedLayer
import dev.sargunv.maplibrekmp.core.source.Source

internal class StyleManager(var style: Style) {
  private val baseLayers = style.getLayers().associateBy { it.id }

  // we queue up additions, but instantly execute removals
  // this way if an id is added and removed in the same frame, it will be removed before it's added
  private val sourcesToAdd = mutableListOf<Source>()
  private val userLayers = mutableListOf<LayerNode<*>>()

  // special handling for Replace anchors
  private val replacedLayers = mutableMapOf<Anchor.Replace, UnspecifiedLayer>()
  private val replacementCounters = mutableMapOf<Anchor.Replace, Int>()

  internal fun addSource(source: Source) {
    // TODO check if source already exists in base style
    sourcesToAdd.add(source)
  }

  internal fun removeSource(source: Source) {
    style.removeSource(source)
  }

  internal fun addLayer(node: LayerNode<*>, index: Int) {
    require(node.layer.id !in baseLayers) {
      "Layer ID '${node.layer.id}' already exists in base style"
    }
    node.anchor.validate(baseLayers)
    userLayers.add(index, node)
  }

  internal fun removeLayer(node: LayerNode<*>, oldIndex: Int) {
    userLayers.removeAt(oldIndex)

    // special handling for Replace anchors
    // restore the original before removing if this layer was the last replacement
    val anchor = node.anchor
    if (anchor is Anchor.Replace) {
      val count = replacementCounters.getValue(anchor) - 1
      if (count > 0) replacementCounters[anchor] = count
      else {
        replacementCounters.remove(anchor)
        style.addLayerBelow(node.layer.id, replacedLayers.remove(anchor)!!)
      }
    }

    style.removeLayer(node.layer)
    node.added = false
  }

  internal fun moveLayer(node: LayerNode<*>, oldIndex: Int, index: Int) {
    removeLayer(node, oldIndex)
    addLayer(node, index)
  }

  internal fun applyChanges() {
    sourcesToAdd.onEach(style::addSource).clear()

    val tailLayerIds = mutableMapOf<Anchor, String>()
    val missedLayers = mutableMapOf<Anchor, MutableList<LayerNode<*>>>()

    userLayers.forEach { node ->
      val layer = node.layer
      val anchor = node.anchor

      if (node.added && anchor in missedLayers) {
        // we found an existing head; let's add the missed layers
        val layersToAdd = missedLayers.remove(anchor)!!
        layersToAdd.forEach { missedLayer ->
          style.addLayerBelow(layer.id, missedLayer.layer)
          missedLayer.markAdded()
        }
      }

      if (!node.added) {
        // we found a layer to add; let's try to add it, or queue it up until we find a head
        tailLayerIds[anchor]?.let { tailLayerId ->
          style.addLayerAbove(tailLayerId, layer)
          node.markAdded()
        } ?: missedLayers.getOrPut(anchor) { mutableListOf() }.add(node)
      }

      // update the tail
      if (node.added) tailLayerIds[anchor] = layer.id
    }

    // anything left in missedLayers is a new anchor
    missedLayers.forEach { (anchor, nodes) ->
      // let's initialize the anchor with one layer
      val tail = nodes.removeLast()
      when (anchor) {
        is Anchor.Top -> style.addLayer(tail.layer)
        is Anchor.Bottom -> style.addLayerAt(0, tail.layer)
        is Anchor.Above -> style.addLayerAbove(anchor.layerId, tail.layer)
        is Anchor.Below -> style.addLayerBelow(anchor.layerId, tail.layer)
        is Anchor.Replace -> {
          val layerToReplace = style.getLayer(anchor.layerId)!!
          style.addLayerAbove(layerToReplace.id, tail.layer)
          style.removeLayer(layerToReplace)
          replacedLayers[anchor] = layerToReplace
          replacementCounters[anchor] = 0
        }
      }
      tail.markAdded()

      // and add the rest below it
      nodes.forEach { node ->
        style.addLayerBelow(tail.layer.id, node.layer)
        node.markAdded()
      }
    }

    // TODO remove this check when I'm confident in the implementation and/or write tests
    require(userLayers.all { node -> node.added }) { "Not all layers were added; this is a bug" }
  }

  private fun LayerNode<*>.markAdded() {
    if (anchor is Anchor.Replace)
      replacementCounters[anchor] = replacementCounters.getValue(anchor) + 1
    added = true
  }
}
