package dev.sargunv.maplibrekmp.internal.compose

import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer
import dev.sargunv.maplibrekmp.style.layer.Anchor

@PublishedApi
internal class LayerNode<T : Layer>(val layer: T, val anchor: Anchor) : MapNode() {
  override fun allowsChild(node: MapNode) = false
}
