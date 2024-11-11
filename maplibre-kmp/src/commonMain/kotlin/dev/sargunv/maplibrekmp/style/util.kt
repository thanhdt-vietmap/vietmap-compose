package dev.sargunv.maplibrekmp.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.compose.StyleNode
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import dev.sargunv.maplibrekmp.style.expression.ExpressionScope
import kotlin.random.Random

@DslMarker internal annotation class MapDsl

@MapDsl public sealed interface LayerContainerScope : ExpressionScope

@MapDsl public data object StyleScope : ExpressionScope

@MapDsl public data object SourceScope : LayerContainerScope

@MapDsl public data object AnchoredLayersScope : LayerContainerScope

internal object IncrementingId : Iterator<String> {
  private var incrementingId = Random.nextInt() % 1000 + 1000

  override fun hasNext(): Boolean = true

  override fun next(): String = "maplibrekmp-${incrementingId++}"
}

public data class SourceHandle internal constructor(val id: String)

@Composable
internal fun getSource(source: SourceHandle): Source? {
  val rootNode = ((currentComposer.applier as MapNodeApplier).root as StyleNode)
  return rootNode.getSource(source.id).value
}
