package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue

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
