package dev.sargunv.maplibrekmp.style.layer

import dev.sargunv.maplibrekmp.style.source.Source

internal expect sealed class Layer {
  abstract val id: String
  abstract val source: Source
}
