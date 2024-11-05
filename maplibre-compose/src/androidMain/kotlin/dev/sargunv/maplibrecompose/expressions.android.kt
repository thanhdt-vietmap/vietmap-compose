package dev.sargunv.maplibrecompose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.maplibre.android.style.expressions.Expression as MlnExpression

private fun toJsonElement(value: Any?): JsonElement =
  when (value) {
    null -> JsonNull.INSTANCE
    is Boolean -> JsonPrimitive(value)
    is Number -> JsonPrimitive(value)
    is String -> JsonPrimitive(value)
    is List<*> -> JsonArray().apply { value.forEach { add(toJsonElement(it)) } }
    is Map<*, *> ->
      JsonObject().apply {
        value.forEach { (key, value) ->
          require(key is String) { "Map keys must be strings" }
          add(key, toJsonElement(value))
        }
      }
    else -> throw IllegalArgumentException("Unsupported type: ${value.javaClass.name}")
  }

internal fun Expression<*>.toMlnExpression() = MlnExpression.Converter.convert(toJsonElement(value))

internal actual fun Color.toMlnColor(): Any =
  toArgb().let { "rgba(${(it shr 16) and 0xFF}, ${(it shr 8) and 0xFF}, ${it and 0xFF}, ${alpha})" }
