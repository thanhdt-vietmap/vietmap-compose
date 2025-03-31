package dev.sargunv.vietmapcompose.compose.engine

import dev.sargunv.vietmapcompose.compose.FeaturesClickHandler
import dev.sargunv.vietmapcompose.compose.layer.Anchor
import dev.sargunv.vietmapcompose.core.layer.Layer

internal class LayerNode<T : Layer>(val layer: T, val anchor: Anchor) : MapNode() {
  internal var added: Boolean = false

  override fun allowsChild(node: MapNode) = false

  internal var onClick: FeaturesClickHandler? = null
  internal var onLongClick: FeaturesClickHandler? = null

  override fun toString(): String {
    return "LayerNode(layer=${layer.id}, anchor=$anchor, added=$added)"
  }
}
