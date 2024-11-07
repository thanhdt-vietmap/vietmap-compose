package dev.sargunv.maplibrekmp.compose

import dev.sargunv.maplibrekmp.style.Style

internal sealed interface InsertionStrategy {

  fun insertAnchorLayer(style: Style, node: MapNode.LayerNode<*>)

  data object Top : InsertionStrategy {
    override fun insertAnchorLayer(style: Style, node: MapNode.LayerNode<*>) =
      style.addLayer(node.layer)
  }

  data object Bottom : InsertionStrategy {
    override fun insertAnchorLayer(style: Style, node: MapNode.LayerNode<*>) =
      style.addLayerAt(0, node.layer)
  }

  data class Below(private val id: String) : InsertionStrategy {
    override fun insertAnchorLayer(style: Style, node: MapNode.LayerNode<*>) =
      style.addLayerBelow(id, node.layer)
  }

  data class Above(private val id: String) : InsertionStrategy {
    override fun insertAnchorLayer(style: Style, node: MapNode.LayerNode<*>) =
      style.addLayerAbove(id, node.layer)
  }
}
