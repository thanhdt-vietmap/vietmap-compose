package vn.vietmap.vietmapcompose.expressions.ast

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import vn.vietmap.vietmapcompose.expressions.ExpressionContext
import vn.vietmap.vietmapcompose.expressions.value.DpValue

/** A [Literal] representing a [Dp] value. */
public data class DpLiteral private constructor(override val value: Dp) : Literal<DpValue, Dp> {

  override fun compile(context: ExpressionContext): CompiledLiteral<DpValue, Float> =
    FloatLiteral.of(value.value).cast()

  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    private val cache = FloatCache { DpLiteral(it.dp) }

    public fun of(value: Dp): DpLiteral = cache[value.value]
  }
}
