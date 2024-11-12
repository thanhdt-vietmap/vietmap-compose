package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source

@PublishedApi
internal expect sealed class Layer {
  abstract val id: String
  abstract val source: Source

  var minZoom: Float
  var maxZoom: Float
  var visible: Boolean
}
