package dev.sargunv.maplibrecompose.compose.engine

import dev.sargunv.maplibrecompose.compose.layer.Anchor
import dev.sargunv.maplibrecompose.core.layer.Layer
import io.github.dellisd.spatialk.geojson.Feature

@PublishedApi
internal class LayerNode<T : Layer>(val layer: T, val anchor: Anchor) : MapNode() {
  internal var added: Boolean = false

  override fun allowsChild(node: MapNode) = false

  internal var onClick: ((List<Feature>) -> Unit)? = null
  internal var onLongClick: ((List<Feature>) -> Unit)? = null

  override fun toString(): String {
    return "LayerNode(layer=${layer.id}, anchor=$anchor, added=$added)"
  }
}
