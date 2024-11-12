package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

internal expect class Style private constructor() {
  fun addSource(source: Source)

  fun removeSource(source: Source)

  fun getSource(id: String): Source?

  fun getSources(): List<Source>

  fun addLayer(layer: Layer)

  fun addLayerAbove(id: String, layer: Layer)

  fun addLayerBelow(id: String, layer: Layer)

  fun addLayerAt(index: Int, layer: Layer)

  fun removeLayer(layer: Layer)

  fun getLayer(id: String): Layer?

  fun getLayers(): List<Layer>
}
