package dev.sargunv.maplibrekmp.core.source

import org.maplibre.android.style.sources.Source

@PublishedApi
internal actual sealed class Source {
  abstract val impl: Source
  actual val id: String
    get() = impl.id
}
