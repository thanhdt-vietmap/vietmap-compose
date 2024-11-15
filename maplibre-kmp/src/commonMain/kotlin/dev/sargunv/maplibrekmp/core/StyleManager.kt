package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.PlatformLayer
import dev.sargunv.maplibrekmp.core.layer.UserLayer
import dev.sargunv.maplibrekmp.core.source.UserSource

internal class StyleManager(val style: Style) {
  private val baseLayers = style.getLayers().associateBy { it.id }

  // we queue up additions, but instantly execute removals
  // this way if an id is added and removed in the same frame, it will be removed before it's added
  private val sourcesToAdd = mutableListOf<UserSource>()
  private val userLayers = mutableListOf<LayerStatus>()

  // special handling for Replace anchors
  private val replacedLayers = mutableMapOf<Anchor.Replace, PlatformLayer>()
  private val replacementCounters = mutableMapOf<Anchor.Replace, Int>()

  private data class LayerStatus(val layer: UserLayer, var added: Boolean = false)

  internal fun addSource(source: UserSource) {
    // TODO check if source already exists in base style
    sourcesToAdd.add(source)
  }

  internal fun removeSource(source: UserSource) {
    style.removeSource(source)
  }

  internal fun addLayer(layer: UserLayer, index: Int) {
    require(layer.id !in baseLayers) { "Layer ID '${layer.id}' already exists in base style" }
    layer.anchor.validate(baseLayers)
    userLayers.add(index, LayerStatus(layer))
  }

  internal fun removeLayer(layer: UserLayer, oldIndex: Int) {
    userLayers.removeAt(oldIndex)

    // special handling for Replace anchors
    // restore the original before removing if this layer was the last replacement
    val anchor = layer.anchor
    if (anchor is Anchor.Replace) {
      val count = replacementCounters.getValue(anchor) - 1
      if (count > 0) replacementCounters[anchor] = count
      else {
        replacementCounters.remove(anchor)
        style.addLayerBelow(layer.id, replacedLayers.remove(anchor)!!)
      }
    }

    style.removeLayer(layer)
  }

  internal fun moveLayer(layer: UserLayer, oldIndex: Int, index: Int) {
    println("moveLayer: $layer, $oldIndex, $index")
    removeLayer(layer, oldIndex)
    addLayer(layer, index)
  }

  internal fun applyChanges() {
    sourcesToAdd.onEach(style::addSource).clear()

    val tailLayerIds = mutableMapOf<Anchor, String>()
    val missedLayers = mutableMapOf<Anchor, MutableList<LayerStatus>>()

    userLayers.forEach {
      val layer = it.layer
      val anchor = layer.anchor

      if (it.added && anchor in missedLayers) {
        // we found an existing head; let's add the missed layers
        val layersToAdd = missedLayers.remove(anchor)!!
        layersToAdd.forEach { missedLayer ->
          style.addLayerBelow(layer.id, missedLayer.layer)
          missedLayer.markAdded()
        }
      }

      if (!it.added) {
        // we found a layer to add; let's try to add it, or queue it up until we find a head
        tailLayerIds[anchor]?.let { tailLayerId ->
          style.addLayerAbove(tailLayerId, layer)
          it.markAdded()
        } ?: missedLayers.getOrPut(anchor) { mutableListOf() }.add(it)
      }

      // update the tail
      if (it.added) tailLayerIds[anchor] = layer.id
    }

    // anything left in missedLayers is a new anchor
    missedLayers.forEach { (anchor, layers) ->
      // let's initialize the anchor with one layer
      val tail = layers.removeLast()
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
      layers.forEach {
        style.addLayerBelow(tail.layer.id, it.layer)
        it.markAdded()
      }
    }

    // TODO remove this check when I'm confident in the implementation and/or write tests
    require(userLayers.all { it.added }) { "Not all layers were added; this is a bug" }
  }

  private fun LayerStatus.markAdded() {
    val anchor = layer.anchor
    if (anchor is Anchor.Replace)
      replacementCounters[anchor] = replacementCounters.getValue(anchor) + 1
    added = true
  }
}
