package dev.sargunv.maplibrekmp.core.layer

@PublishedApi
internal expect sealed class UserLayer(anchor: Anchor) : Layer {
  val anchor: Anchor
  var minZoom: Float
  var maxZoom: Float
  var visible: Boolean
}
