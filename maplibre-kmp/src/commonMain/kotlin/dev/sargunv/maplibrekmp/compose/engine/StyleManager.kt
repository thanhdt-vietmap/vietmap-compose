package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.compose.layer.Anchor
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

internal class StyleManager(var style: Style) {
  private val baseSources = style.getSources().associateBy { it.id }
  private val baseLayers = style.getLayers().associateBy { it.id }

  // we queue up additions, but instantly execute removals
  // this way if an id is added and removed in the same frame, it will be removed before it's added
  private val sourcesToAdd = mutableListOf<Source>()
  private val userLayers = mutableListOf<LayerNode<*>>()

  // special handling for Replace anchors
  private val replacedLayers = mutableMapOf<Anchor.Replace, Layer>()
  private val replacementCounters = mutableMapOf<Anchor.Replace, Int>()

  internal fun getBaseSource(id: String): Source {
    return baseSources[id] ?: error("Source ID '$id' not found in base style")
  }

  internal fun addSource(source: Source) {
    require(source.id !in baseSources) { "Source ID '${source.id}' already exists in base style" }
    println("Queuing source ${source.id} for addition")
    sourcesToAdd.add(source)
  }

  internal fun removeSource(source: Source) {
    println("Removing source ${source.id}")
    style.removeSource(source)
  }

  internal fun addLayer(node: LayerNode<*>, index: Int) {
    require(node.layer.id !in baseLayers) {
      "Layer ID '${node.layer.id}' already exists in base style"
    }
    node.anchor.validate()
    println("Queuing layer ${node.layer.id} for addition at anchor ${node.anchor}, index $index")
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
        println("Restoring layer ${anchor.layerId}")
        style.addLayerBelow(node.layer.id, replacedLayers.remove(anchor)!!)
      }
    }

    println("Removing layer ${node.layer.id}")
    style.removeLayer(node.layer)
    node.added = false
  }

  internal fun moveLayer(node: LayerNode<*>, oldIndex: Int, index: Int) {
    println("Moving layer ${node.layer.id} from $oldIndex to $index")
    removeLayer(node, oldIndex)
    addLayer(node, index)
    println("Done moving layer ${node.layer.id}")
  }

  internal fun applyChanges() {
    println("Before applying changes: ${style.getLayers().map { it.id }}")

    sourcesToAdd
      .onEach {
        println("Adding source ${it.id}")
        style.addSource(it)
      }
      .clear()

    val tailLayerIds = mutableMapOf<Anchor, String>()
    val missedLayers = mutableMapOf<Anchor, MutableList<LayerNode<*>>>()

    userLayers.forEach { node ->
      val layer = node.layer
      val anchor = node.anchor

      if (node.added && anchor in missedLayers) {
        // we found an existing head; let's add the missed layers
        val layersToAdd = missedLayers.remove(anchor)!!
        layersToAdd.forEach { missedLayer ->
          println("Adding layer ${missedLayer.layer.id} below ${layer.id}")
          style.addLayerBelow(layer.id, missedLayer.layer)
          missedLayer.markAdded()
        }
      }

      if (!node.added) {
        // we found a layer to add; let's try to add it, or queue it up until we find a head
        tailLayerIds[anchor]?.let { tailLayerId ->
          println("Adding layer ${layer.id} above $tailLayerId")
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
      println("Adding layer ${tail.layer.id} with anchor $anchor")
      when (anchor) {
        is Anchor.Top -> style.addLayer(tail.layer)
        is Anchor.Bottom -> style.addLayerAt(0, tail.layer)
        is Anchor.Above -> style.addLayerAbove(anchor.layerId, tail.layer)
        is Anchor.Below -> style.addLayerBelow(anchor.layerId, tail.layer)
        is Anchor.Replace -> {
          val layerToReplace = style.getLayer(anchor.layerId)!!
          style.addLayerAbove(layerToReplace.id, tail.layer)
          println("Replacing layer ${layerToReplace.id} with ${tail.layer.id}")
          style.removeLayer(layerToReplace)
          replacedLayers[anchor] = layerToReplace
          replacementCounters[anchor] = 0
        }
      }
      tail.markAdded()

      // and add the rest below it
      nodes.forEach { node ->
        println("Adding layer ${node.layer.id} below ${tail.layer.id}")
        style.addLayerBelow(tail.layer.id, node.layer)
        node.markAdded()
      }
    }

    println("After applying changes: ${style.getLayers().map { it.id }}")

    // TODO remove this check when I'm confident in the implementation and/or write tests
    require(userLayers.all { node -> node.added }) { "Not all layers were added; this is a bug" }
  }

  private fun LayerNode<*>.markAdded() {
    if (anchor is Anchor.Replace)
      replacementCounters[anchor] = replacementCounters.getValue(anchor) + 1
    added = true
  }

  private fun Anchor.validate() {
    when (this) {
      is Anchor.WithLayerId ->
        require(baseLayers.containsKey(layerId)) { "Layer ID '$layerId' not found in base style" }
      else -> Unit
    }
  }
}
