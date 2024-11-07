package dev.sargunv.maplibrekmp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.ExpressionScope
import dev.sargunv.maplibrekmp.style.layer.LineLayer
import dev.sargunv.maplibrekmp.style.source.GeoJsonSource
import dev.sargunv.maplibrekmp.style.source.Source
import kotlin.random.Random

@DslMarker internal annotation class MapDsl

@MapDsl public data object StyleScope : LayerStackScope

@MapDsl public sealed interface LayerStackScope : ExpressionScope

private var incrementingId = Random.nextInt() % 1000 + 1000

private fun nextId(): String = "maplibrekmp-${incrementingId++}"

@Composable
internal fun withSource(id: String, block: @Composable (source: Source) -> Unit) {
  val rootNode = ((currentComposer.applier as MapNodeApplier).root as MapNode.StyleNode)
  val source by rootNode.getSource(id)
  source?.let { block(it) }
}

@Composable
public fun StyleScope.GeoJsonSource(url: String, tolerance: Float? = null): String {
  val id = remember { nextId() }
  key(id, url, tolerance) {
    ComposeNode<MapNode.SourceNode<GeoJsonSource>, MapNodeApplier>(
      factory = { MapNode.SourceNode(GeoJsonSource(id = id, url = url, tolerance = tolerance)) },
      update = {},
    )
  }
  return id
}

@Composable
public fun LayerStackScope.LineLayer(
  sourceId: String,
  lineColor: Expression<Color>,
  lineWidth: Expression<Number>,
) {
  val id = remember { nextId() }
  withSource(sourceId) { source ->
    ComposeNode<MapNode.LayerNode<LineLayer>, MapNodeApplier>(
      factory = { MapNode.LayerNode(LineLayer(id = id, source = source)) },
      update = {
        set(lineColor) { layer.setColor(it) }
        set(lineWidth) { layer.setWidth(it) }
      },
    )
  }
}

@Composable
public fun StyleScope.StackBelow(layerId: String, content: @Composable LayerStackScope.() -> Unit) {
  ComposeNode<MapNode.LayerStackNode, MapNodeApplier>(
    factory = { MapNode.LayerStackNode(InsertionStrategy.Below(layerId)) },
    update = {},
  ) {
    content()
  }
}

@Composable
public fun StyleScope.StackAbove(layerId: String, content: @Composable LayerStackScope.() -> Unit) {
  ComposeNode<MapNode.LayerStackNode, MapNodeApplier>(
    factory = { MapNode.LayerStackNode(InsertionStrategy.Above(layerId)) },
    update = {},
  ) {
    content()
  }
}

@Composable
public fun StyleScope.StackBottom(content: @Composable LayerStackScope.() -> Unit) {
  ComposeNode<MapNode.LayerStackNode, MapNodeApplier>(
    factory = { MapNode.LayerStackNode(InsertionStrategy.Bottom) },
    update = {},
  ) {
    content()
  }
}
