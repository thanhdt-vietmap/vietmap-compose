package dev.sargunv.maplibrekmp.map

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.expressionWithMLNJSONObject
import dev.sargunv.maplibrekmp.style.Expression
import platform.Foundation.NSExpression
import platform.UIKit.UIColor

internal object NativeExpressionAdapter : StyleManager.Adapter<Expression<*>, NSExpression> {

  override fun convert(common: Expression<*>): NSExpression =
    common.value?.let { NSExpression.expressionWithMLNJSONObject(normalizeJsonLike(it)!!) }
      ?: NSExpression.expressionForConstantValue(null)

  private fun normalizeJsonLike(value: Any?): Any? =
    when (value) {
      null -> null
      is Boolean -> value
      is Number -> value
      is String -> value
      is List<*> -> value.map(::normalizeJsonLike)
      is Map<*, *> -> value.mapValues { normalizeJsonLike(it.value) }
      is Color ->
        UIColor.colorWithRed(
          red = value.red.toDouble(),
          green = value.green.toDouble(),
          blue = value.blue.toDouble(),
          alpha = value.alpha.toDouble(),
        )
      else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
    }
}
