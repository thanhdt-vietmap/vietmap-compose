package dev.sargunv.maplibrekmp.map

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.expressionWithMLNJSONObject
import dev.sargunv.maplibrekmp.style.Expression
import platform.Foundation.NSExpression
import platform.UIKit.UIColor

private fun convertToMlnJson(value: Any?): Any? =
  when (value) {
    null -> null
    is Boolean -> value
    is Number -> value
    is String -> value
    is List<*> -> value.map(::convertToMlnJson)
    is Map<*, *> -> value.mapValues { convertToMlnJson(it.value) }
    is Color ->
      UIColor.colorWithRed(
        red = value.red.toDouble(),
        green = value.green.toDouble(),
        blue = value.blue.toDouble(),
        alpha = value.alpha.toDouble(),
      )
    else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
  }

internal fun Expression<*>.toNSExpression() =
  value?.let { NSExpression.expressionWithMLNJSONObject(convertToMlnJson(it)!!) }
    ?: NSExpression.expressionForConstantValue(null)
