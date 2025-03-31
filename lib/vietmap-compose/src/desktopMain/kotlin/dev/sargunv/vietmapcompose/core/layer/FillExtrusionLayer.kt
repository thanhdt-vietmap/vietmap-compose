package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.ImageValue
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor

internal actual class FillExtrusionLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setFillExtrusionOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setFillExtrusionColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setFillExtrusionTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setFillExtrusionTranslateAnchor(anchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }

  actual fun setFillExtrusionPattern(pattern: CompiledExpression<ImageValue>) {
    TODO()
  }

  actual fun setFillExtrusionHeight(height: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setFillExtrusionBase(base: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setFillExtrusionVerticalGradient(verticalGradient: CompiledExpression<BooleanValue>) {
    TODO()
  }
}
