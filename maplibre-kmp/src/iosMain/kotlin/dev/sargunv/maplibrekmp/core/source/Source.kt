package dev.sargunv.maplibrekmp.core.source

import cocoapods.MapLibre.MLNSource

@PublishedApi
internal actual sealed class Source {
  abstract val impl: MLNSource
  actual val id: String
    get() = impl.identifier
}
