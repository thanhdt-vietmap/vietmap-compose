package vn.vietmap.vietmapcompose.expressions.value

import vn.vietmap.vietmapcompose.expressions.ast.StringLiteral
import vn.vietmap.vietmapcompose.expressions.dsl.type

/** The type of value resolved from an expression, as returned by [type]. */
public enum class ExpressionType(override val literal: StringLiteral) : EnumValue<ExpressionType> {
  Number(StringLiteral.of("number")),
  String(StringLiteral.of("string")),
  Object(StringLiteral.of("object")),
  Boolean(StringLiteral.of("boolean")),
  Color(StringLiteral.of("color")),
  Array(StringLiteral.of("array")),
}
