package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.layer.UserLayer

@PublishedApi
internal class LayerNode<T : UserLayer>(val layer: T) : MapNode() {
  override fun allowsChild(node: MapNode) = false
}
