package vn.vietmap.vietmapcompose.expressions.ast

import vn.vietmap.vietmapcompose.expressions.ExpressionContext
import vn.vietmap.vietmapcompose.expressions.value.ExpressionValue

/** An [Expression] that evaluates to a value of type [T]. */
public sealed interface Expression<out T : ExpressionValue> {
  /** Transform this expression into the equivalent [CompiledExpression]. */
  public fun compile(context: ExpressionContext): CompiledExpression<T>

  public fun visit(block: (Expression<*>) -> Unit)

  @Suppress("UNCHECKED_CAST")
  public fun <X : ExpressionValue> cast(): Expression<X> = this as Expression<X>
}
