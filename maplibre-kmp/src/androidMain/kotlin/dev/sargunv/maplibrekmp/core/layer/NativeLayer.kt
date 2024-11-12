package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import org.maplibre.android.style.layers.Layer as MLNLayer

@PublishedApi
internal actual class NativeLayer private actual constructor(override val id: String) : Layer() {
  private lateinit var _impl: MLNLayer

  constructor(layer: MLNLayer) : this(layer.id) {
    _impl = layer
  }

  override val impl
    get() = _impl

  override val source: Source
    get() = TODO("Not yet implemented")
}
