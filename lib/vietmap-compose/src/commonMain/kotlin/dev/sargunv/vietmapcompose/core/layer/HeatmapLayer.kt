package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue

internal expect class HeatmapLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: CompiledExpression<BooleanValue>)

  fun setHeatmapRadius(radius: CompiledExpression<DpValue>)

  fun setHeatmapWeight(weight: CompiledExpression<FloatValue>)

  fun setHeatmapIntensity(intensity: CompiledExpression<FloatValue>)

  fun setHeatmapColor(color: CompiledExpression<ColorValue>)

  fun setHeatmapOpacity(opacity: CompiledExpression<FloatValue>)
}
