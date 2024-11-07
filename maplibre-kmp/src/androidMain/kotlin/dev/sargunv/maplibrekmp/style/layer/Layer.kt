package dev.sargunv.maplibrekmp.style.layer

import dev.sargunv.maplibrekmp.style.source.Source
import org.maplibre.android.style.layers.Layer

internal actual sealed class Layer {
  actual abstract val id: String
  actual abstract val source: Source
  abstract val impl: Layer
}
