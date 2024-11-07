package dev.sargunv.maplibrekmp.style

import dev.sargunv.maplibrekmp.style.layer.Layer
import dev.sargunv.maplibrekmp.style.source.Source

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
}
