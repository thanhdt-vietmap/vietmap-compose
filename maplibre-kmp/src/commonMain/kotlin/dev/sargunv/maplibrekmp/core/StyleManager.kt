package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Stable
import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.PlatformLayer
import dev.sargunv.maplibrekmp.core.layer.UserLayer
import dev.sargunv.maplibrekmp.core.source.UserSource
import kotlin.math.min

@Stable
internal class StyleManager(val style: Style) {
  // we queue up additions, but instantly execute removals
  // this way if an id is added and removed in the same frame, it will be removed before it's added
  private val sourcesToAdd = mutableListOf<UserSource>()
  private val layersToAdd = mutableListOf<LayerLocation>()

  // TODO red black tree instead of ArrayList? since we're inserting/removing at arbitrary index
  private val layerLocations = mutableMapOf<Anchor, MutableList<LayerLocation>>()
  private val removedLayers = mutableMapOf<Anchor.Replace, PlatformLayer>()

  private data class LayerLocation(
    var index: Int,
    val layer: UserLayer,
    var added: Boolean = false,
  )

  internal fun addSource(source: UserSource) {
    println("addSource: $source")
    sourcesToAdd.add(source)
  }

  internal fun removeSource(source: UserSource) {
    println("removeSource: $source")
    style.removeSource(source)
  }

  internal fun addLayer(layer: UserLayer, index: Int) {
    println("addLayer: $layer, $index")
    val newLoc = LayerLocation(index, layer)
    var newAnchor = true

    for ((a, locs) in layerLocations.entries) {
      val pos = locs.binarySearch { it.index.compareTo(index) }.invIfNegative()

      // increment indexes after the new layer's index
      locs.subList(min(pos + 1, locs.size), locs.size).forEach { it.index++ }

      // if this is the list where the layer belongs, add it
      if (a == layer.anchor) {
        newAnchor = false
        locs.add(pos, newLoc)
      }
    }

    // if the layer introduced a new anchor, create a new list
    if (newAnchor) {
      layerLocations[layer.anchor] = mutableListOf(newLoc)
    }

    // and queue the layer for addition
    layersToAdd.add(newLoc)
  }

  internal fun removeLayer(layer: UserLayer, oldIndex: Int) {
    println("removeLayer: $layer, $oldIndex")
    var empty = false
    for ((a, locs) in layerLocations.entries) {
      val pos = locs.binarySearch { it.index.compareTo(oldIndex) }.invIfNegative()

      // if the layer is in this list, remove it
      if (a == layer.anchor) locs.removeAt(pos)
      if (locs.isEmpty()) empty = true

      // decrement indexes after the removed layer's index
      locs.subList(pos, locs.size).forEach { it.index-- }
    }
    if (empty) {
      layerLocations.remove(layer.anchor)
      if (layer.anchor is Anchor.Replace) {
        val removed = removedLayers.remove(layer.anchor)!!
        style.addLayerBelow(layer.id, removed)
      }
    }
    style.removeLayer(layer)
  }

  internal fun moveLayer(layer: UserLayer, oldIndex: Int, index: Int) {
    println("moveLayer: $layer, $oldIndex, $index")
    for ((a, locs) in layerLocations.entries) {
      // if the layer is in this list, remove it from its current position
      val oldPos = locs.binarySearch { it.index.compareTo(oldIndex) }.invIfNegative()
      val movedLoc = if (a == layer.anchor) locs.removeAt(oldPos) else null

      // and add it to its new position
      val newPos = locs.binarySearch { it.index.compareTo(index) }.invIfNegative()
      if (a == layer.anchor) {
        locs.add(newPos, movedLoc!!)
        movedLoc.index = index
      }

      // adjust indexes for layers between the old and new positions
      if (oldPos < newPos) locs.subList(oldPos, newPos).forEach { it.index-- }
      else locs.subList(newPos + 1, oldPos + 1).forEach { it.index++ }

      // if the layer moved relative to other layers in this list, remove and queue re-addition
      if (a == layer.anchor && oldPos != newPos) {
        style.removeLayer(layer)
        layersToAdd.add(movedLoc!!)
      }
    }
  }

  internal fun applyChanges() {
    // sources are easy to add
    sourcesToAdd.onEach(style::addSource).clear()

    // layers are tricky
    layersToAdd
      .onEach { (index, layer) ->
        // find our layer in the list
        val locs = layerLocations[layer.anchor]!!
        val pos = locs.binarySearch { it.index.compareTo(index) }

        // add above the previous layer, if possible
        val prevLayer = locs.subList(0, pos).lastOrNull { it.added }?.layer
        if (prevLayer != null) style.addLayerAbove(prevLayer.id, layer)
        else {
          // add below the next layer, if possible
          val nextLayer = locs.subList(pos + 1, locs.size).firstOrNull { it.added }?.layer
          if (nextLayer != null) style.addLayerBelow(nextLayer.id, layer)
          else {
            // otherwise, we're the first layer for this anchor, so add as per the anchor
            when (val anchor = layer.anchor) {
              is Anchor.Top -> style.addLayer(layer)
              is Anchor.Bottom -> style.addLayerAt(0, layer)
              is Anchor.Above -> style.addLayerAbove(anchor.layerId, layer)
              is Anchor.Below -> style.addLayerBelow(anchor.layerId, layer)
              is Anchor.Replace -> {
                val removedLayer = style.getLayer(anchor.layerId)!!
                style.addLayerBelow(removedLayer.id, layer)
                style.removeLayer(removedLayer)
                removedLayers[anchor] = removedLayer
              }
            }
          }
        }

        locs[pos].added = true
      }
      .clear()
  }

  private fun Int.invIfNegative() = if (this < 0) this.inv() else this
}
