package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.Layer
import dev.sargunv.maplibrekmp.style.Source

internal abstract class StyleManager<NativeSource, NativeLayer>(
  private val sourceAdapter: Adapter<Source, NativeSource>,
  private val layerAdapter: Adapter<Layer, NativeLayer>,
) {
  private val registeredSources = mutableSetOf<Source>()

  internal abstract fun registerSource(source: NativeSource)

  internal abstract fun registerLayer(layer: NativeLayer)

  internal abstract fun registerLayerAbove(id: String, layer: NativeLayer)

  internal abstract fun registerLayerBelow(id: String, layer: NativeLayer)

  internal abstract fun registerLayerAt(index: Int, layer: NativeLayer)

  internal abstract fun hasSource(id: String): Boolean

  internal abstract fun hasLayer(id: String): Boolean

  internal fun addLayer(layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayer(layerAdapter.convert(layer))
  }

  internal fun addLayerAbove(above: String, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerAbove(above, layerAdapter.convert(layer))
  }

  internal fun addLayerBelow(below: String, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerBelow(below, layerAdapter.convert(layer))
  }

  internal fun addLayerAt(index: Int, layer: Layer) {
    checkLayerId(layer.id)
    registerSourceIfNotExists(layer.source)
    registerLayerAt(index, layerAdapter.convert(layer))
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
      registerSource(sourceAdapter.convert(source))
      registeredSources.add(source)
    }
  }

  internal fun interface Adapter<Common, Native> {
    fun convert(common: Common): Native
  }
}
