package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.Layer
import dev.sargunv.maplibrekmp.style.Source

internal abstract class StyleManager {
  private val registeredSources = mutableSetOf<Source>()

  internal abstract fun registerSource(source: Source)

  internal abstract fun registerLayer(layer: Layer)

  internal abstract fun registerLayerAbove(id: String, layer: Layer)

  internal abstract fun registerLayerBelow(id: String, layer: Layer)

  internal abstract fun registerLayerAt(index: Int, layer: Layer)

  internal abstract fun hasSource(id: String): Boolean

  internal abstract fun hasLayer(id: String): Boolean

  internal fun addLayer(layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayer(layer)
  }

  internal fun addLayerAbove(above: String, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerAbove(above, layer)
  }

  internal fun addLayerBelow(below: String, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerBelow(below, layer)
  }

  internal fun addLayerAt(index: Int, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerAt(index, layer)
  }

  private fun checkLayerId(id: String) {
    if (hasLayer(id)) {
      throw IllegalArgumentException("Layer with id '${id}' already registered")
    }
  }

  private fun checkSourceId(id: String) {
    if (hasSource(id)) {
      throw IllegalArgumentException("Source with id '${id}' already registered")
    }
  }

  private fun registerSourceIfNotExists(source: Source) {
    if (!registeredSources.contains(source)) {
      checkSourceId(source.id)
      registerSource(source)
      registeredSources.add(source)
    }
  }
}
