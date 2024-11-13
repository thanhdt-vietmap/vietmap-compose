package dev.sargunv.maplibrekmp.core

import dev.sargunv.maplibrekmp.core.layer.BaseLayer
import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.core.source.BaseSource
import dev.sargunv.maplibrekmp.core.source.Source
import org.maplibre.android.maps.Style as MLNStyle

internal actual class Style private actual constructor() {

  private lateinit var impl: MLNStyle

  internal constructor(style: MLNStyle) : this() {
    impl = style
  }

  actual fun addSource(source: Source) {
    impl.addSource(source.impl)
  }

  actual fun removeSource(source: Source) {
    impl.removeSource(source.id)
  }

  actual fun getSource(id: String): Source? {
    return impl.getSource(id)?.let { BaseSource(it) }
  }

  actual fun getSources(): List<Source> {
    return impl.sources.map { BaseSource(it) }
  }

  actual fun addLayer(layer: Layer) {
    println("+Adding layer ${layer.id}")
    println("Layers: ${impl.layers.joinToString { it.id }}")
    impl.addLayer(layer.impl)
  }

  actual fun addLayerAbove(id: String, layer: Layer) {
    println("+Adding layer ${layer.id} above $id")
    println("Layers: ${impl.layers.joinToString { it.id }}")
    impl.addLayerAbove(layer.impl, id)
  }

  actual fun addLayerBelow(id: String, layer: Layer) {
    println("+Adding layer ${layer.id} below $id")
    println("Layers: ${impl.layers.joinToString { it.id }}")
    impl.addLayerBelow(layer.impl, id)
  }

  actual fun addLayerAt(index: Int, layer: Layer) {
    println("+Adding layer ${layer.id} at $index")
    println("Layers: ${impl.layers.joinToString { it.id }}")
    impl.addLayerAt(layer.impl, index)
  }

  actual fun removeLayer(layer: Layer) {
    println("+Removing layer ${layer.id}")
    println("Layers: ${impl.layers.joinToString { it.id }}")
    impl.removeLayer(layer.id)
  }

  actual fun getLayer(id: String): Layer? {
    return impl.getLayer(id)?.let { BaseLayer(it) }
  }

  actual fun getLayers(): List<Layer> {
    return impl.layers.map { BaseLayer(it) }
  }
}
