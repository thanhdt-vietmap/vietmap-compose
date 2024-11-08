package dev.sargunv.maplibrekmp.internal.compose

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.internal.wrapper.Style
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal class StyleNode(val style: Style) : MapNode() {

  private var knownSources =
    mutableStateMapOf<String, Source>().apply { style.getSources().forEach { put(it.id, it) } }

  override fun allowsChild(node: MapNode) = node is SourceNode<*>

  override fun onChildInserted(style: Style, index: Int, node: MapNode) {
    val sourceNode = node as SourceNode<*>
    knownSources[sourceNode.source.id] = sourceNode.source
    style.addSource(sourceNode.source)
  }

  override fun onChildRemoved(style: Style, node: MapNode) {
    val sourceNode = node as SourceNode<*>
    knownSources.remove(sourceNode.source.id)
    style.removeSource(sourceNode.source)
  }

  internal fun getSource(id: String) = derivedStateOf { knownSources[id] }
}
