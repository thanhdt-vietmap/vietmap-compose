package dev.sargunv.maplibrekmp.core.layer

import org.maplibre.android.style.layers.Layer as MlnLayer

@PublishedApi
internal actual sealed class Layer {
  abstract val impl: MlnLayer
  actual val id: String
    get() = impl.id

  override fun toString() = "${this::class.simpleName}(id=\"$id\")"
}
