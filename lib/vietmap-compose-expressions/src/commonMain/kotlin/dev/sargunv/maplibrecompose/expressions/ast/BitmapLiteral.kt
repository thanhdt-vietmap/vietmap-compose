package dev.sargunv.maplibrecompose.expressions.ast

import androidx.compose.ui.graphics.ImageBitmap
import dev.sargunv.maplibrecompose.expressions.ExpressionContext
import dev.sargunv.maplibrecompose.expressions.value.StringValue

/**
 * A [Literal] representing an [ImageBitmap] value, which will be loaded as an image into the style
 * upon compilation.
 */
public data class BitmapLiteral
private constructor(override val value: ImageBitmap, val sdf: Boolean) :
  Literal<StringValue, ImageBitmap> {
  override fun compile(context: ExpressionContext): StringLiteral =
    StringLiteral.of(context.resolveBitmap(this))

  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    public fun of(value: ImageBitmap, sdf: Boolean): BitmapLiteral = BitmapLiteral(value, sdf)
  }
}
