package vn.vietmap.vietmapcompose.core.layer

import cocoapods.VietMap.MLNBackgroundStyleLayer
import vn.vietmap.vietmapcompose.core.util.toNSExpression
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.ast.NullLiteral
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.ImageValue

internal actual class BackgroundLayer actual constructor(id: String) : Layer() {
  override val impl = MLNBackgroundStyleLayer(id)

  actual fun setBackgroundColor(color: CompiledExpression<ColorValue>) {
    impl.backgroundColor = color.toNSExpression()
  }

  actual fun setBackgroundPattern(pattern: CompiledExpression<ImageValue>) {
    // TODO: figure out how to unset a pattern in iOS
    if (pattern != NullLiteral) {
      impl.backgroundPattern = pattern.toNSExpression()
    }
  }

  actual fun setBackgroundOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.backgroundOpacity = opacity.toNSExpression()
  }
}
