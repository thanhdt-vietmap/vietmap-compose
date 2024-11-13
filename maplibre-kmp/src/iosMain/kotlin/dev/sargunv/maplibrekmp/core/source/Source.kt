package dev.sargunv.maplibrekmp.core.source

import cocoapods.MapLibre.MLNSource

public actual sealed class Source {
  internal abstract val impl: MLNSource
  internal actual val id: String
    get() = impl.identifier

  override fun toString() = "${this::class.simpleName}(id=\"$id\")"
}
