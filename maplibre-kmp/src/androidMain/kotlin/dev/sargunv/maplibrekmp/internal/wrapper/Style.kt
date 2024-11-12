package dev.sargunv.maplibrekmp.internal.wrapper

import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer
import dev.sargunv.maplibrekmp.internal.wrapper.layer.NativeLayer
import dev.sargunv.maplibrekmp.internal.wrapper.source.NativeSource
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
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

  actual fun getLayer(id: String): Layer? {
    return impl.getLayer(id)?.let { NativeLayer(it) }
  }

  actual fun getLayers(): List<Layer> {
    return impl.layers.map { NativeLayer(it) }
  }
}
