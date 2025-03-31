package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpOffsetValue
import vn.vietmap.vietmapcompose.expressions.value.DpValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue
import vn.vietmap.vietmapcompose.expressions.value.LineCap
import vn.vietmap.vietmapcompose.expressions.value.LineJoin
import vn.vietmap.vietmapcompose.expressions.value.TranslateAnchor
import vn.vietmap.vietmapcompose.expressions.value.VectorValue

internal actual class LineLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setLineCap(cap: CompiledExpression<LineCap>) {
    TODO()
  }

  actual fun setLineJoin(join: CompiledExpression<LineJoin>) {
    TODO()
  }

  actual fun setLineMiterLimit(miterLimit: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setLineRoundLimit(roundLimit: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setLineSortKey(sortKey: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setLineOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setLineColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setLineTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setLineTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }

  actual fun setLineWidth(width: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setLineGapWidth(gapWidth: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setLineOffset(offset: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setLineBlur(blur: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setLineDasharray(dasharray: CompiledExpression<VectorValue<Number>>) {
    TODO()
  }

  actual fun setLinePattern(pattern: CompiledExpression<ImageValue>) {
    TODO()
  }

  actual fun setLineGradient(gradient: CompiledExpression<ColorValue>) {
    TODO()
  }
}
