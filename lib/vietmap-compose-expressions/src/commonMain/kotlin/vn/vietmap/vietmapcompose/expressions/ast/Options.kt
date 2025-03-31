package vn.vietmap.vietmapcompose.expressions.ast

import vn.vietmap.vietmapcompose.expressions.ExpressionContext
import vn.vietmap.vietmapcompose.expressions.value.ExpressionValue
import vn.vietmap.vietmapcompose.expressions.value.MapValue

/** An [Expression] representing a JSON object with values all [Expression]. */
public data class Options<T : ExpressionValue>
private constructor(val value: Map<String, Expression<T>>) : Expression<MapValue<T>> {

  override fun compile(context: ExpressionContext): CompiledOptions<T> =
    CompiledOptions.of(value.mapValues { it.value.compile(context) })

  override fun visit(block: (Expression<*>) -> Unit) {
    block(this)
    value.values.forEach { it.visit(block) }
  }

  public companion object {
    internal fun build(block: MutableMap<String, Expression<*>>.() -> Unit) =
      Options(mutableMapOf<String, Expression<*>>().apply(block).mapValues { it.value })
  }
}
