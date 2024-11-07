package dev.sargunv.maplibrekmp.style.source

import cocoapods.MapLibre.MLNSource

internal actual sealed class Source {
  actual abstract val id: String
  abstract val impl: MLNSource
}
