package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor

internal expect class FillExtrusionLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: CompiledExpression<BooleanValue>)

  fun setFillExtrusionOpacity(opacity: CompiledExpression<FloatValue>)

  fun setFillExtrusionColor(color: CompiledExpression<ColorValue>)

  fun setFillExtrusionTranslate(translate: CompiledExpression<DpOffsetValue>)

  fun setFillExtrusionTranslateAnchor(anchor: CompiledExpression<TranslateAnchor>)

  fun setFillExtrusionPattern(pattern: CompiledExpression<ImageValue>)

  fun setFillExtrusionHeight(height: CompiledExpression<FloatValue>)

  fun setFillExtrusionBase(base: CompiledExpression<FloatValue>)

  fun setFillExtrusionVerticalGradient(verticalGradient: CompiledExpression<BooleanValue>)
}
