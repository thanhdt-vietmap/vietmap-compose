package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source

@PublishedApi
internal expect sealed class UserLayer(source: Source, anchor: Anchor) : Layer {
  val source: Source
  val anchor: Anchor
  var minZoom: Float
  var maxZoom: Float
  var visible: Boolean
}
