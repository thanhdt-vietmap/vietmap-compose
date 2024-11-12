package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.layer.Layer
import dev.sargunv.maplibrekmp.compose.layer.Anchor

@PublishedApi
internal class LayerNode<T : Layer>(val layer: T, val anchor: Anchor) : MapNode() {
  override fun allowsChild(node: MapNode) = false
}
