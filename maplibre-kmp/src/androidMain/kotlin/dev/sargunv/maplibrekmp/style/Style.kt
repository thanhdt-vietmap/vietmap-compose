package dev.sargunv.maplibrekmp.style

import dev.sargunv.maplibrekmp.style.layer.Layer
import dev.sargunv.maplibrekmp.style.source.NativeSource
import dev.sargunv.maplibrekmp.style.source.Source
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
    return impl.getSource(id)?.let { NativeSource(it) }
  }

  actual fun getSources(): List<Source> {
    return impl.sources.map { NativeSource(it) }
  }

  actual fun addLayer(layer: Layer) {
    impl.addLayer(layer.impl)
  }

  actual fun addLayerAbove(id: String, layer: Layer) {
    impl.addLayerAbove(layer.impl, id)
  }

  actual fun addLayerBelow(id: String, layer: Layer) {
    impl.addLayerBelow(layer.impl, id)
  }

  actual fun addLayerAt(index: Int, layer: Layer) {
    impl.addLayerAt(layer.impl, index)
  }

  actual fun removeLayer(layer: Layer) {
    impl.removeLayer(layer.id)
  }
}
