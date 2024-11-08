package dev.sargunv.maplibrekmp.internal.wrapper.layer

import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal actual sealed class Layer {
  actual abstract val id: String
  actual abstract val source: Source
  abstract val impl: MLNStyleLayer
}
