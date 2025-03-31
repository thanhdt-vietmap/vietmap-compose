package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor

internal expect class FillLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: CompiledExpression<BooleanValue>)

  fun setFillSortKey(sortKey: CompiledExpression<FloatValue>)

  fun setFillAntialias(antialias: CompiledExpression<BooleanValue>)

  fun setFillOpacity(opacity: CompiledExpression<FloatValue>)

  fun setFillColor(color: CompiledExpression<ColorValue>)

  fun setFillOutlineColor(outlineColor: CompiledExpression<ColorValue>)

  fun setFillTranslate(translate: CompiledExpression<DpOffsetValue>)

  fun setFillTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>)

  fun setFillPattern(pattern: CompiledExpression<ImageValue>)
}
