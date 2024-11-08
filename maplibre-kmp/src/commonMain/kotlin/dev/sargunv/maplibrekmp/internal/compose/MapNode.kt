package dev.sargunv.maplibrekmp.internal.compose

import dev.sargunv.maplibrekmp.internal.wrapper.Style

internal sealed class MapNode {
  val children = mutableListOf<MapNode>()

  abstract fun allowsChild(node: MapNode): Boolean

  open fun onChildInserted(style: Style, index: Int, node: MapNode) {}

  open fun onChildRemoved(style: Style, node: MapNode) {}

  open fun onChildMovedLeft(style: Style, newIndex: Int, node: MapNode) {}

  open fun onChildMovedRight(style: Style, newIndex: Int, node: MapNode) {}
}
