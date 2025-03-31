package dev.sargunv.maplibrecompose.expressions.ast

import androidx.compose.ui.geometry.Offset
import dev.sargunv.maplibrecompose.expressions.value.FloatOffsetValue

/** A [Literal] representing a [Offset] value. */
public data class OffsetLiteral private constructor(override val value: Offset) :
  CompiledLiteral<FloatOffsetValue, Offset> {
  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    private val zero = OffsetLiteral(Offset.Zero)

    public fun of(value: Offset): OffsetLiteral =
      if (value == Offset.Zero) zero else OffsetLiteral(value)
  }
}
