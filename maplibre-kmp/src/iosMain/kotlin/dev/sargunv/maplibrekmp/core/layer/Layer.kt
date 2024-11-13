package dev.sargunv.maplibrekmp.core.layer

import cocoapods.MapLibre.MLNStyleLayer

@PublishedApi
internal actual sealed class Layer {
  abstract val impl: MLNStyleLayer
  actual val id: String
    get() = impl.identifier

  override fun toString() = "${this::class.simpleName}(id=\"$id\")"
}
