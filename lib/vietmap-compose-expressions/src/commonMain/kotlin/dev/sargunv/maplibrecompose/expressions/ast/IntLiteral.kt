package dev.sargunv.maplibrecompose.expressions.ast

import dev.sargunv.maplibrecompose.expressions.ExpressionContext
import dev.sargunv.maplibrecompose.expressions.value.IntValue

/** A [Literal] representing an [Int] value. */
public data class IntLiteral private constructor(override val value: Int) : Literal<IntValue, Int> {

  override fun compile(context: ExpressionContext): CompiledLiteral<IntValue, Float> =
    FloatLiteral.of(value.toFloat()).cast()

  override fun visit(block: (Expression<*>) -> Unit): Unit = block(this)

  public companion object {
    private val cache = IntCache(::IntLiteral)

    public fun of(value: Int): IntLiteral = cache[value]
  }
}
