package dev.sargunv.maplibrekmp.compose.engine

import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager

internal class StyleNode(style: Style) : MapNode() {

  internal val styleManager = StyleManager(style)

  override fun allowsChild(node: MapNode) = node is LayerNode<*>

  override fun onChildRemoved(oldIndex: Int, node: MapNode) {
    node as LayerNode<*>
    styleManager.enqueueRemoveLayer(node.layer, node.anchor, oldIndex)
  }

  override fun onChildInserted(index: Int, node: MapNode) {
    node as LayerNode<*>
    styleManager.enqueueAddLayer(node.layer, node.anchor, index)
  }

  override fun onChildMoved(oldIndex: Int, index: Int, node: MapNode) {
    node as LayerNode<*>
    styleManager.enqueueMoveLayer(node.layer, node.anchor, oldIndex, index)
  }

  override fun onEndChanges() {
    styleManager.applyChanges()
  }
}
