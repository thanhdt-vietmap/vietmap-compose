package dev.sargunv.maplibrekmp.internal.wrapper.layer

import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal expect sealed class Layer {
  abstract val id: String
  abstract val source: Source

  var minZoom: Float
  var maxZoom: Float
  var visible: Boolean
}
