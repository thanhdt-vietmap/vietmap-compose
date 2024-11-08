package dev.sargunv.maplibrekmp.internal.compose

import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.layer.LayerAnchor

internal class SourceNode<T : Source>(val source: T) : LayerContainerNode() {
  override val anchor = LayerAnchor.Top

  override fun allowsChild(node: MapNode) = super.allowsChild(node) || node is AnchoredLayersNode
}
