package dev.sargunv.vietmapcompose.expressions.ast

import androidx.compose.foundation.layout.PaddingValues
import dev.sargunv.vietmapcompose.expressions.ZeroPadding
import dev.sargunv.vietmapcompose.expressions.value.DpPaddingValue

/** A [Literal] representing a [PaddingValues] value. */
public data class DpPaddingLiteral private constructor(override val value: PaddingValues.Absolute) :
  CompiledLiteral<DpPaddingValue, PaddingValues.Absolute> {
  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    private val zero = DpPaddingLiteral(ZeroPadding)

    public fun of(value: PaddingValues.Absolute): DpPaddingLiteral =
      if (value == ZeroPadding) zero else DpPaddingLiteral(value)
  }
}
