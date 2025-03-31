package dev.sargunv.vietmapcompose.expressions.ast

import dev.sargunv.vietmapcompose.expressions.ExpressionContext
import dev.sargunv.vietmapcompose.expressions.value.ExpressionValue

/**
 * An [Expression] reduced to only those data types supported by the VietMap SDKs. This can be
 * thought of as an intermediate representation between the high level expression DSL and the
 * platform-specific encoding.
 */
public sealed interface CompiledExpression<out T : ExpressionValue> : Expression<T> {
  override fun compile(context: ExpressionContext): CompiledExpression<T> = this

  @Suppress("UNCHECKED_CAST")
  override fun <X : ExpressionValue> cast(): CompiledExpression<X> = this as CompiledExpression<X>
}
