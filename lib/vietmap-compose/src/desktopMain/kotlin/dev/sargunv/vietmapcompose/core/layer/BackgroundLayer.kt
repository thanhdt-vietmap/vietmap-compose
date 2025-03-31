package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.ImageValue

internal actual class BackgroundLayer actual constructor(id: String) : Layer() {

  override val impl: Nothing = TODO()

  actual fun setBackgroundColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setBackgroundPattern(pattern: CompiledExpression<ImageValue>) {
    TODO()
  }

  actual fun setBackgroundOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }
}
