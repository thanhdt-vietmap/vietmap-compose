package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.layer.PlatformLayer
import dev.sargunv.maplibrekmp.core.source.Source

internal expect class Style private constructor() {
  fun addSource(source: Source)

  fun removeSource(source: Source)

  fun getLayer(id: String): PlatformLayer?

  fun getLayers(): List<PlatformLayer>

  fun addLayer(layer: Layer)

  fun addLayerAbove(id: String, layer: Layer)

  fun addLayerBelow(id: String, layer: Layer)

  fun addLayerAt(index: Int, layer: Layer)

  fun removeLayer(layer: Layer)
}
