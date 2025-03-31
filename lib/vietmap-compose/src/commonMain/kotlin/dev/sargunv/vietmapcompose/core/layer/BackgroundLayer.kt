package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.ImageValue

internal expect class BackgroundLayer(id: String) : Layer {
  fun setBackgroundColor(color: CompiledExpression<ColorValue>)

  fun setBackgroundPattern(pattern: CompiledExpression<ImageValue>)

  fun setBackgroundOpacity(opacity: CompiledExpression<FloatValue>)
}
