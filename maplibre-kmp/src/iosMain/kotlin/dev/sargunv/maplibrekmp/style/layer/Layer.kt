package dev.sargunv.maplibrekmp.style.layer

import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.style.source.Source

internal actual sealed class Layer {
  actual abstract val id: String
  actual abstract val source: Source
  abstract val impl: MLNStyleLayer
}
