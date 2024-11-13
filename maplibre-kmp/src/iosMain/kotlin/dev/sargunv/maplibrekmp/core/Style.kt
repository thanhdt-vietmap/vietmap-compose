package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNStyle
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.Source

internal actual class Style private actual constructor() {
  private lateinit var impl: MLNStyle

  internal constructor(style: MLNStyle) : this() {
    impl = style
  }

  actual fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  actual fun removeSource(source: Source) {
    impl.removeSource(source.impl)
  }

  actual fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  actual fun addLayerAbove(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, aboveLayer = impl.layerWithIdentifier(id)!!)
  }

  actual fun addLayerBelow(id: String, layer: Layer) {
    impl.insertLayer(layer.impl, belowLayer = impl.layerWithIdentifier(id)!!)
  }

  actual fun addLayerAt(index: Int, layer: Layer) {
    impl.insertLayer(layer.impl, atIndex = index.toULong())
  }

  actual fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.impl)
  }
}
