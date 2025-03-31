package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue

internal expect sealed class FeatureLayer(source: Source) : Layer {
  val source: Source
  abstract var sourceLayer: String

  abstract fun setFilter(filter: CompiledExpression<BooleanValue>)
}
