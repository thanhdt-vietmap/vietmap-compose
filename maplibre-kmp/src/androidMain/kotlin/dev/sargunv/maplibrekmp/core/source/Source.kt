package dev.sargunv.maplibrekmp.core.source

import org.maplibre.android.style.sources.Source

@PublishedApi
internal actual sealed class Source {
  actual abstract val id: String
  abstract val impl: Source
}
