package dev.sargunv.maplibrekmp.style.expression

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// would make this an inline value class, but we lose varargs
// https://youtrack.jetbrains.com/issue/KT-33565/Allow-vararg-parameter-of-inline-class-type
@Immutable
public data class Expression<out T> private constructor(internal val value: Any?) {
  internal companion object {
    fun ofString(string: String): Expression<String> = Expression(string)

    fun ofNumber(number: Number): Expression<Number> = Expression(number)

    fun ofBoolean(bool: Boolean): Expression<Boolean> = Expression(bool)

    fun ofNull(): Expression<Nothing?> = Expression(null)

    fun ofColor(color: Color): Expression<Color> = Expression(color)

    fun ofPoint(point: Point): Expression<Point> = Expression(point)

    // return Expression<*> because without ["literal" ... ] MapLibre may not treat it as a list
    fun ofList(list: List<Expression<*>>): Expression<*> = Expression<Any?>(list.map { it.value })

    // return Expression<*> because without ["literal" ... ] MapLibre may not treat it as a list
    fun ofMap(map: Map<String, Expression<*>>): Expression<*> =
      Expression<Any?>(map.entries.associate { (key, value) -> key to value.value })
  }
}
