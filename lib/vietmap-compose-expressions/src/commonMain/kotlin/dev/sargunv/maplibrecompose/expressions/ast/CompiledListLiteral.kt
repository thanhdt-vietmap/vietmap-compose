package dev.sargunv.maplibrecompose.expressions.ast

import dev.sargunv.maplibrecompose.expressions.value.ExpressionValue
import dev.sargunv.maplibrecompose.expressions.value.ListValue

/** A [Literal] representing a JSON array with elements all [CompiledLiteral]. */
public data class CompiledListLiteral<T : ExpressionValue>
private constructor(override val value: List<CompiledLiteral<T, *>>) :
  CompiledLiteral<ListValue<T>, List<Literal<T, *>>> {
  override fun visit(block: (Expression<*>) -> Unit) {
    block(this)
    value.forEach { it.visit(block) }
  }

  public companion object {
    internal fun <T : ExpressionValue> of(
      value: List<CompiledLiteral<T, *>>
    ): CompiledListLiteral<T> = CompiledListLiteral(value)
  }
}
