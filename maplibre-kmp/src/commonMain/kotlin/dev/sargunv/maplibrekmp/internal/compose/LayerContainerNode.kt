package dev.sargunv.maplibrekmp.internal.compose

import dev.sargunv.maplibrekmp.internal.wrapper.Style
import dev.sargunv.maplibrekmp.style.layer.LayerAnchor

internal sealed class LayerContainerNode() : MapNode() {
  abstract val anchor: LayerAnchor

  override fun allowsChild(node: MapNode) = node is LayerNode<*>

  override fun onChildRemoved(style: Style, node: MapNode) {
    val layerNode = node as? LayerNode<*> ?: return
    style.removeLayer(layerNode.layer)
  }

  override fun onChildInserted(style: Style, index: Int, node: MapNode) {
    val layerNode = node as? LayerNode<*> ?: return
    findPrevLayer(index)?.let { style.addLayerAbove(it.layer.id, layerNode.layer) }
      ?: findNextLayer(index)?.let { style.addLayerBelow(it.layer.id, layerNode.layer) }
      ?: when (val anchor = anchor) {
        LayerAnchor.Top -> style.addLayer(layerNode.layer)
        LayerAnchor.Bottom -> style.addLayerAt(0, layerNode.layer)
        is LayerAnchor.Above -> style.addLayerAbove(anchor.layerId, layerNode.layer)
        is LayerAnchor.Below -> style.addLayerBelow(anchor.layerId, layerNode.layer)
      }
  }

  override fun onChildMovedLeft(style: Style, newIndex: Int, node: MapNode) {
    val layerNode = node as? LayerNode<*> ?: return
    style.removeLayer(layerNode.layer)
    findNextLayer(newIndex)!!.let { style.addLayerBelow(it.layer.id, layerNode.layer) }
  }

  override fun onChildMovedRight(style: Style, newIndex: Int, node: MapNode) {
    val layerNode = node as? LayerNode<*> ?: return
    style.removeLayer(layerNode.layer)
    findPrevLayer(newIndex)!!.let { style.addLayerAbove(it.layer.id, layerNode.layer) }
  }

  private fun findNextLayer(fromIndex: Int): LayerNode<*>? {
    return children
      .subList(fromIndex + 1, children.size)
      .asSequence()
      .filterIsInstance<LayerNode<*>>()
      .firstOrNull()
  }

  private fun findPrevLayer(fromIndex: Int): LayerNode<*>? {
    return children
      .subList(0, fromIndex)
      .asReversed()
      .asSequence()
      .filterIsInstance<LayerNode<*>>()
      .firstOrNull()
  }
}
