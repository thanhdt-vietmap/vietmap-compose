package dev.sargunv.maplibrekmp.compose.engine

import androidx.compose.runtime.AbstractApplier

internal class MapNodeApplier(root: StyleNode) : AbstractApplier<MapNode>(root) {
  override fun insertBottomUp(index: Int, instance: MapNode) {}

  override fun insertTopDown(index: Int, instance: MapNode) {
    current.allowsChild(instance)
    current.children.add(index, instance)
    current.onChildInserted(index, instance)
  }

  override fun move(from: Int, to: Int, count: Int) {
    val moved = current.children.slice(from until (from + count - 1))
    current.children.move(from, to, count)
    moved.forEachIndexed { i, instance -> current.onChildMoved(from + i, to + i, instance) }
  }

  override fun onClear() = remove(0, current.children.size)

  override fun remove(index: Int, count: Int) {
    val removed = current.children.slice(index until (index + count - 1))
    current.children.remove(index, count)
    removed.forEachIndexed { i, instance -> current.onChildRemoved(i + index, instance) }
  }
}
