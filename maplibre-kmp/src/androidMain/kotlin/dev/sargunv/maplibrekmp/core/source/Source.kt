package dev.sargunv.maplibrekmp.core.source

import org.maplibre.android.style.sources.Source as MLNSource

public actual sealed class Source {
  internal abstract val impl: MLNSource
  internal actual val id: String
    get() = impl.id

  override fun toString(): String = "${this::class.simpleName}(id=\"$id\")"
}
