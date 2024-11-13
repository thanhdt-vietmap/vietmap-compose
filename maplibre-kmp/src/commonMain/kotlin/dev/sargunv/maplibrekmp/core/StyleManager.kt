package dev.sargunv.maplibrekmp.core

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.core.layer.Anchor
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

@Stable
internal class StyleManager(val style: Style) {

  private val userSources = mutableStateMapOf<String, Source>()
  private val userLayers = mutableStateMapOf<String, Layer>()

  private val sourcesToRemove = mutableListOf<Source>()
  private val layersToRemove = mutableListOf<Layer>()
  private val sourcesToAdd = mutableListOf<Source>()
  private val layersToAdd = mutableListOf<Pair<Anchor, Layer>>()

  private val layerAnchorList = mutableListOf<Pair<Anchor, Layer>>()
  private var isLayersDirty = false

  internal fun enqueueAddSource(source: Source) {
    println("Adding source ${source.id}")
    sourcesToAdd.add(source)
  }

  internal fun enqueueRemoveSource(source: Source) {
    println("Removing source ${source.id}")
    sourcesToRemove.add(source)
  }

  internal fun enqueueAddLayer(layer: Layer, anchor: Anchor, index: Int) {
    println("Adding layer ${layer.id} with anchor $anchor and index $index")
    layerAnchorList.add(index, anchor to layer)
    layersToAdd.add(anchor to layer)
  }

  internal fun enqueueRemoveLayer(layer: Layer, anchor: Anchor, index: Int) {
    println("Removing layer ${layer.id} with anchor $anchor and sort key $index")
    layerAnchorList.removeAt(index)
    layersToRemove.add(layer)
  }

  internal fun enqueueMoveLayer(layer: Layer, anchor: Anchor, oldIndex: Int, index: Int) {
    println("Moving layer ${layer.id} with anchor $anchor from $oldIndex to $index")
    layerAnchorList.removeAt(oldIndex)
    layerAnchorList.add(index, anchor to layer)
    isLayersDirty = true
  }

  internal fun applyChanges() {
    println("Applying changes")

    sourcesToRemove
      .onEach {
        style.removeSource(it)
        userSources.remove(it.id)
      }
      .clear()

    sourcesToAdd
      .onEach {
        style.addSource(it)
        userSources[it.id] = it
      }
      .clear()

    // TODO something better than removing and re-adding all layers

    if (isLayersDirty || layersToRemove.isNotEmpty() || layersToAdd.isNotEmpty()) {
      // remove all user layers
      userLayers.values.forEach { style.removeLayer(it) }
      layersToRemove.onEach { userLayers.remove(it.id) }.clear()

      // re-add all user layers
      val prevLayerIdForAnchor = mutableMapOf<Anchor, String?>()
      layerAnchorList.forEach { (anchor, layer) ->
        val prevLayerId = prevLayerIdForAnchor[anchor]
        if (prevLayerId != null) style.addLayerAbove(prevLayerId, layer)
        else
          when (anchor) {
            is Anchor.Top -> style.addLayer(layer)
            is Anchor.Bottom -> style.addLayerAt(0, layer)
            is Anchor.Below -> style.addLayerBelow(anchor.layerId, layer)
            is Anchor.Above -> style.addLayerAbove(anchor.layerId, layer)
          }
        prevLayerIdForAnchor[anchor] = layer.id
      }
      layersToAdd.onEach { (_, layer) -> userLayers[layer.id] = layer }.clear()

      isLayersDirty = false
    }
  }
}
