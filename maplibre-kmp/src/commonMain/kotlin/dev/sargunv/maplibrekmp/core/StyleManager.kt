package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Stable
import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.UserLayer
import dev.sargunv.maplibrekmp.core.source.UserSource
import kotlin.math.min

@Stable
internal class StyleManager(val style: Style) {
  // we queue up additions, but instantly execute removals
  // this way if an id is added and removed in the same frame, it will be removed before it's added
  private val sourcesToAdd = mutableListOf<UserSource>()
  private val layersToAdd = mutableListOf<Pair<UserLayer, Anchor>>()

  // TODO red black tree instead of ArrayList? since we're inserting/removing at arbitrary index
  private val layerLocations = mutableMapOf<Anchor, MutableList<LayerLocation>>()

  private data class LayerLocation(var index: Int, val layer: UserLayer)

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

        // and queue the layer to be added to the style above/below its neighbor
        if (pos == 0) layersToAdd.add(layer to Anchor.Below(locs[pos + 1].layer.id))
        else layersToAdd.add(layer to Anchor.Above(locs[pos - 1].layer.id))
      }
    }

    // if the layer introduced a new anchor, create a new list and queue the layer to be added
    if (newAnchor) {
      layerLocations[layer.anchor] = mutableListOf(newLoc)
      layersToAdd.add(layer to layer.anchor)
    }
  }

  internal fun removeLayer(layer: UserLayer, oldIndex: Int) {
    println("removeLayer: $layer, $oldIndex")
    for ((a, locs) in layerLocations.entries) {
      val pos = locs.binarySearch { it.index.compareTo(oldIndex) }.invIfNegative()

      // if the layer is in this list, remove it
      if (a == layer.anchor) locs.removeAt(pos)

      // decrement indexes after the removed layer's index
      locs.subList(pos, locs.size).forEach { it.index-- }
    }
    style.removeLayer(layer)
  }

  internal fun moveLayer(layer: UserLayer, oldIndex: Int, index: Int) {
    println("moveLayer: $layer, $oldIndex, $index")
    for ((a, locs) in layerLocations.entries) {
      // if the layer is in this list, remove it from its current position
      val oldPos = locs.binarySearch { it.index.compareTo(oldIndex) }.invIfNegative()
      if (a == layer.anchor) locs.removeAt(oldPos)

      // and add it to its new position
      val newPos = locs.binarySearch { it.index.compareTo(index) }.invIfNegative()
      if (a == layer.anchor) locs.add(newPos, LayerLocation(index, layer))

      // adjust indexes for layers between the old and new positions
      if (oldPos < newPos) locs.subList(oldPos, newPos).forEach { it.index-- }
      else locs.subList(newPos + 1, oldPos + 1).forEach { it.index++ }

      // if the layer moved relative to other layers in this list, remove and queue re-addition
      if (a == layer.anchor && oldPos != newPos) {
        style.removeLayer(layer)
        if (newPos == 0) layersToAdd.add(layer to Anchor.Below(locs[newPos + 1].layer.id))
        else layersToAdd.add(layer to Anchor.Above(locs[newPos - 1].layer.id))
      }
    }
  }

  internal fun applyChanges() {
    sourcesToAdd.onEach(style::addSource).clear()
    layersToAdd
      .onEach { (layer, anchor) ->
        when (anchor) {
          is Anchor.Top -> style.addLayer(layer)
          is Anchor.Bottom -> style.addLayerAt(0, layer)
          is Anchor.Above -> style.addLayerAbove(anchor.layerId, layer)
          is Anchor.Below -> style.addLayerBelow(anchor.layerId, layer)
        }
      }
      .clear()
  }

  private fun Int.invIfNegative() = if (this < 0) this.inv() else this
}
