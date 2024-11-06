package dev.sargunv.maplibrekmp.style.expression

import androidx.compose.ui.graphics.Color

// would make this an inline value class, but we lose varargs
// https://youtrack.jetbrains.com/issue/KT-33565/Allow-vararg-parameter-of-inline-class-type
public data class Expression<out T> internal constructor(internal val value: Any?) {
  internal companion object {
    fun ofString(string: String): Expression<String> = Expression(string)

    fun ofNumber(number: Number): Expression<Number> = Expression(number)

    fun ofBoolean(bool: Boolean): Expression<Boolean> = Expression(bool)

    fun ofNull(): Expression<Nothing?> = Expression(null)

    fun ofColor(color: Color): Expression<Color> = Expression(color)

    fun ofList(list: List<Expression<*>>): Expression<List<*>> = Expression(list.map { it.value })

    fun ofMap(map: Map<String, Expression<*>>): Expression<Map<String, *>> =
      Expression(map.entries.associate { (key, value) -> key to value.value })
  }
}
