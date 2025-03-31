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

internal actual class CircleLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setCircleSortKey(sortKey: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setCircleRadius(radius: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setCircleColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setCircleBlur(blur: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setCircleOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setCircleTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setCircleTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }

  actual fun setCirclePitchScale(pitchScale: CompiledExpression<CirclePitchScale>) {
    TODO()
  }

  actual fun setCirclePitchAlignment(pitchAlignment: CompiledExpression<CirclePitchAlignment>) {
    TODO()
  }

  actual fun setCircleStrokeWidth(strokeWidth: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setCircleStrokeColor(strokeColor: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setCircleStrokeOpacity(strokeOpacity: CompiledExpression<FloatValue>) {
    TODO()
  }
}
