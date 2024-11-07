package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.AbstractApplier
import dev.sargunv.maplibrekmp.style.Style

internal class MapNodeApplier(root: MapNode.StyleNode) : AbstractApplier<MapNode>(root) {
  private val container: MapNode.NodeContainer
    get() = current as MapNode.NodeContainer

  private val styleNode: MapNode.StyleNode
    get() = root as MapNode.StyleNode

  private val style: Style
    get() = (root as MapNode.StyleNode).style

  override fun insertBottomUp(index: Int, instance: MapNode) {
    //    println("insertBottomUp: $index, $instance")
  }

  override fun insertTopDown(index: Int, instance: MapNode) {
    //    println("insertTopDown: $index, $instance")
    container.children.add(index, instance)
    onInserted(index, instance)
  }

  override fun move(from: Int, to: Int, count: Int) {
    val moved = container.children.slice(from until (from + count - 1))
    //    println("move: $from -> $to: $moved")
    container.children.move(from, to, count)
    if (to > from) {
      moved.forEachIndexed { index, instance -> onMovedRight(to + index, instance) }
    } else {
      moved.asReversed().forEachIndexed { index, instance ->
        onMovedLeft(to + moved.size - 1 - index, instance)
      }
    }
  }

  override fun onClear() {
    //    println("onClear: ${container.children}")
    container.children.forEach(::onRemoved)
    container.children.clear()
  }

  override fun remove(index: Int, count: Int) {
    val removed = container.children.slice(index until (index + count - 1))
    //    println("remove: $index, $count: $removed")
    container.children.remove(index, count)
    removed.forEach(::onRemoved)
  }

  private fun onInserted(index: Int, instance: MapNode) {
    when (instance) {
      is MapNode.StyleNode -> throw IllegalStateException("Style nodes cannot be nested")
      is MapNode.SourceNode<*> -> styleNode.addSource(instance.source)
      is MapNode.LayerNode<*> ->
        findPrevLayer(index)?.let { style.addLayerAbove(it.layer.id, instance.layer) }
          ?: findNextLayer(index)?.let { style.addLayerBelow(it.layer.id, instance.layer) }
          ?: container.insertionStrategy.insertAnchorLayer(style, instance)
      is MapNode.LayerStackNode -> {}
    }
  }

  private fun onMovedLeft(newIndex: Int, instance: MapNode) {
    when (instance) {
      is MapNode.StyleNode -> throw IllegalStateException("Style nodes cannot be nested")
      is MapNode.SourceNode<*> -> {}
      is MapNode.LayerNode<*> -> {
        style.removeLayer(instance.layer)
        findNextLayer(newIndex)!!.let { style.addLayerBelow(it.layer.id, instance.layer) }
      }
      is MapNode.LayerStackNode -> {}
    }
  }

  private fun onMovedRight(newIndex: Int, instance: MapNode) {
    when (instance) {
      is MapNode.StyleNode -> throw IllegalStateException("Style nodes cannot be nested")
      is MapNode.SourceNode<*> -> {}
      is MapNode.LayerNode<*> -> {
        style.removeLayer(instance.layer)
        findPrevLayer(newIndex)!!.let { style.addLayerAbove(it.layer.id, instance.layer) }
      }
      is MapNode.LayerStackNode -> {}
    }
  }

  private fun onRemoved(instance: MapNode) {
    when (instance) {
      is MapNode.StyleNode -> throw IllegalStateException("Style nodes cannot be nested")
      is MapNode.SourceNode<*> -> styleNode.removeSource(instance.source)
      is MapNode.LayerNode<*> -> style.removeLayer(instance.layer)
      is MapNode.LayerStackNode -> {}
    }
  }

  private fun findNextLayer(fromIndex: Int): MapNode.LayerNode<*>? {
    return container.children
      .subList(fromIndex + 1, container.children.size)
      .asSequence()
      .filterIsInstance<MapNode.LayerNode<*>>()
      .firstOrNull()
  }

  private fun findPrevLayer(fromIndex: Int): MapNode.LayerNode<*>? {
    return container.children
      .subList(0, fromIndex)
      .asReversed()
      .asSequence()
      .filterIsInstance<MapNode.LayerNode<*>>()
      .firstOrNull()
  }
}
