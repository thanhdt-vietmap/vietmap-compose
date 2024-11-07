package dev.sargunv.maplibrekmp.style.source

import org.maplibre.android.style.sources.Source

internal actual sealed class Source {
  actual abstract val id: String
  abstract val impl: Source
}
