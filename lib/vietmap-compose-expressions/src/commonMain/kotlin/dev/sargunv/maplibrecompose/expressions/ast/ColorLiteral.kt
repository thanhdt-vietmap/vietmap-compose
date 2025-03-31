package dev.sargunv.maplibrecompose.expressions.ast

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrecompose.expressions.value.ColorValue

/** A [Literal] representing a [Color] value. */
public data class ColorLiteral private constructor(override val value: Color) :
  CompiledLiteral<ColorValue, Color> {
  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    private val black = ColorLiteral(Color.Black)
    private val white = ColorLiteral(Color.White)
    private val transparent = ColorLiteral(Color.Transparent)

    public fun of(value: Color): ColorLiteral =
      when (value) {
        Color.Black -> black
        Color.White -> white
        Color.Transparent -> transparent
        else -> ColorLiteral(value)
      }
  }
}
