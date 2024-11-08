package dev.sargunv.maplibrekmp.internal.compose

import dev.sargunv.maplibrekmp.internal.wrapper.layer.Layer

internal class LayerNode<T : Layer>(val layer: T) : MapNode() {
  override fun allowsChild(node: MapNode) = false
}
