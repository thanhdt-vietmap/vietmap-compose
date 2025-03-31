package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.CirclePitchAlignment
import vn.vietmap.vietmapcompose.expressions.value.CirclePitchScale
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.DpValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor

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
