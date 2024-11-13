package dev.sargunv.maplibrekmp.core.source

import org.maplibre.android.style.sources.Source as MLNSource

@PublishedApi
internal actual sealed class Source {
  abstract val impl: MLNSource
  actual val id: String
    get() = impl.id

  override fun toString() = "${this::class.simpleName}(id=\"$id\")"
}
