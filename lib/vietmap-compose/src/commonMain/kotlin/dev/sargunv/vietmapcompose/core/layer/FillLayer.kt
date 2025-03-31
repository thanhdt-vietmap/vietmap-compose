package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.ImageValue
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor

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
