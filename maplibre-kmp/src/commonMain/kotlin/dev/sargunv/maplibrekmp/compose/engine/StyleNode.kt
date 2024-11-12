package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager

internal class StyleNode(style: Style) : MapNode() {

  internal val styleManager = StyleManager(style)

  override fun allowsChild(node: MapNode) = node is LayerNode<*>

  override fun onChildRemoved(oldIndex: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.removeLayer(layerNode.layer, layerNode.anchor, oldIndex)
  }

  override fun onChildInserted(index: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.addLayer(layerNode.layer, layerNode.anchor, index)
  }

  override fun onChildMoved(oldIndex: Int, index: Int, node: MapNode) {
    val layerNode = node as LayerNode<*>
    styleManager.removeLayer(layerNode.layer, layerNode.anchor, oldIndex)
    styleManager.addLayer(layerNode.layer, layerNode.anchor, index)
  }
}
