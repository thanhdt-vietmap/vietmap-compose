package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.layer.UserLayer
import io.github.dellisd.spatialk.geojson.Feature

@PublishedApi
internal class LayerNode<T : UserLayer>(val layer: T) : MapNode() {
  override fun allowsChild(node: MapNode) = false

  internal var onClick: ((List<Feature>) -> Unit)? = null
  internal var onLongClick: ((List<Feature>) -> Unit)? = null
}
