package dev.sargunv.maplibrekmp.internal.wrapper.source

import org.maplibre.android.style.sources.Source

@PublishedApi
internal actual sealed class Source {
  actual abstract val id: String
  abstract val impl: Source
}
