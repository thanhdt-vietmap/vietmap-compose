package dev.sargunv.vietmapcompose.compose.engine

import dev.sargunv.vietmapcompose.core.source.Source

internal class SourceManager(private val node: StyleNode) {

  private val baseSources = node.style.getSources().associateBy { it.id }
  private val sourcesToAdd = mutableListOf<Source>()
  private val counter = ReferenceCounter<Source>()

  internal fun getBaseSource(id: String): Source {
    return baseSources[id] ?: error("Source ID '$id' not found in base style")
  }

  internal fun addReference(source: Source) {
    require(source.id !in baseSources) { "Source ID '${source.id}' already exists in base style" }
    counter.increment(source) {
      node.logger?.i { "Queuing source ${source.id} for addition" }
      sourcesToAdd.add(source)
    }
  }

  internal fun removeReference(source: Source) {
    require(source.id !in baseSources) {
      "Source ID '${source.id}' is part of the base style and can't be removed here"
    }
    counter.decrement(source) {
      node.logger?.i { "Removing source ${source.id}" }
      node.style.removeSource(source)
    }
  }

  internal fun applyChanges() {
    sourcesToAdd
      .onEach {
        node.logger?.i { "Adding source ${it.id}" }
        node.style.addSource(it)
      }
      .clear()
  }
}
