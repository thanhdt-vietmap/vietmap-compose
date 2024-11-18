package dev.sargunv.maplibrekmp.core.layer

@PublishedApi
internal expect sealed class UserLayer() : Layer {
  var minZoom: Float
  var maxZoom: Float
  var visible: Boolean
}
