package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor

internal actual class FillLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setFillSortKey(sortKey: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setFillAntialias(antialias: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setFillOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setFillColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setFillOutlineColor(outlineColor: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setFillTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setFillTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }

  actual fun setFillPattern(pattern: CompiledExpression<ImageValue>) {
    TODO()
  }
}
