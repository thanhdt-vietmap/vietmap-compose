package dev.sargunv.vietmapcompose.expressions.ast

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import dev.sargunv.vietmapcompose.expressions.ExpressionContext
import dev.sargunv.vietmapcompose.expressions.value.StringValue

/**
 * A [Literal] representing a [Painter] value, which will be drawn to a bitmap and loaded as an
 * image into the style upon compilation.
 */
public data class PainterLiteral
private constructor(override val value: Painter, val size: DpSize?, val sdf: Boolean) :
  Literal<StringValue, Painter> {
  override fun compile(context: ExpressionContext): StringLiteral =
    StringLiteral.of(context.resolvePainter(this))

  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    public fun of(value: Painter, size: DpSize?, sdf: Boolean): PainterLiteral =
      PainterLiteral(value, size, sdf)
  }
}
