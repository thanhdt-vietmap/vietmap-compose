package dev.sargunv.maplibrekmp.internal.compose

import androidx.compose.runtime.AbstractApplier

internal class MapNodeApplier(root: StyleNode) : AbstractApplier<MapNode>(root) {
  private val rootNode: StyleNode
    get() = root as StyleNode

  override fun insertBottomUp(index: Int, instance: MapNode) {}

  override fun insertTopDown(index: Int, instance: MapNode) {
    current.allowsChild(instance)
    current.children.add(index, instance)
    current.onChildInserted(rootNode.style, index, instance)
  }

  override fun move(from: Int, to: Int, count: Int) {
    val moved = current.children.slice(from until (from + count - 1))
    current.children.move(from, to, count)
    if (to > from) {
      moved.forEachIndexed { index, instance ->
        current.onChildMovedRight(rootNode.style, to + index, instance)
      }
    } else {
      moved.asReversed().forEachIndexed { index, instance ->
        current.onChildMovedLeft(rootNode.style, to + moved.size - 1 - index, instance)
      }
    }
  }

  override fun onClear() {
    current.children.forEach { current.onChildRemoved(rootNode.style, it) }
    current.children.clear()
  }

  override fun remove(index: Int, count: Int) {
    val removed = current.children.slice(index until (index + count - 1))
    current.children.remove(index, count)
    removed.forEach { current.onChildRemoved(rootNode.style, it) }
  }
}
