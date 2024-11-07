package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import dev.sargunv.maplibrekmp.style.Style
import dev.sargunv.maplibrekmp.style.layer.Layer
import dev.sargunv.maplibrekmp.style.source.Source

internal sealed interface MapNode {
  class StyleNode(val style: Style) : MapNode, NodeContainer {
    override val children = mutableListOf<MapNode>()
    override val insertionStrategy = InsertionStrategy.Top
    private var knownSources =
      mutableStateMapOf<String, Source>().apply { style.getSources().forEach { put(it.id, it) } }

    internal fun addSource(source: Source) {
      knownSources[source.id] = source
      style.addSource(source)
    }

    internal fun removeSource(source: Source) {
      knownSources.remove(source.id)
      style.removeSource(source)
    }

    internal fun getSource(id: String) = derivedStateOf { knownSources[id] }
  }

  class LayerStackNode(override val insertionStrategy: InsertionStrategy) : MapNode, NodeContainer {
    override val children = mutableListOf<MapNode>()
  }

  class SourceNode<T : Source>(val source: T) : MapNode

  class LayerNode<T : Layer>(val layer: T) : MapNode

  sealed interface LayerStack {
    val insertionStrategy: InsertionStrategy
  }

  sealed interface NodeContainer : LayerStack {
    val children: MutableList<MapNode>
  }
}
