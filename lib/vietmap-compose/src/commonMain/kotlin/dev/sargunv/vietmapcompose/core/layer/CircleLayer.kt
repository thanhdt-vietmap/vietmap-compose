package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchAlignment
import dev.sargunv.vietmapcompose.expressions.value.CirclePitchScale
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor

internal expect class CircleLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: CompiledExpression<BooleanValue>)

  fun setCircleSortKey(sortKey: CompiledExpression<FloatValue>)

  fun setCircleRadius(radius: CompiledExpression<DpValue>)

  fun setCircleColor(color: CompiledExpression<ColorValue>)

  fun setCircleBlur(blur: CompiledExpression<FloatValue>)

  fun setCircleOpacity(opacity: CompiledExpression<FloatValue>)

  fun setCircleTranslate(translate: CompiledExpression<DpOffsetValue>)

  fun setCircleTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>)

  fun setCirclePitchScale(pitchScale: CompiledExpression<CirclePitchScale>)

  fun setCirclePitchAlignment(pitchAlignment: CompiledExpression<CirclePitchAlignment>)

  fun setCircleStrokeWidth(strokeWidth: CompiledExpression<DpValue>)

  fun setCircleStrokeColor(strokeColor: CompiledExpression<ColorValue>)

  fun setCircleStrokeOpacity(strokeOpacity: CompiledExpression<FloatValue>)
}
