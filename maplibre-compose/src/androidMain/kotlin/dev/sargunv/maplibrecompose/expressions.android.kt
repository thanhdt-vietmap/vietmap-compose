package dev.sargunv.maplibrecompose

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

public actual class Color internal constructor(color: String) : Expression<TColor> {
  @Suppress("RedundantNullableReturnType") override val value: Any? = color
}

public actual object Colors {
  public actual val white: Color = Color("white")
  public actual val silver: Color = Color("silver")
  public actual val gray: Color = Color("gray")
  public actual val black: Color = Color("black")
  public actual val red: Color = Color("red")
  public actual val maroon: Color = Color("maroon")
  public actual val yellow: Color = Color("yellow")
  public actual val green: Color = Color("green")
  public actual val blue: Color = Color("blue")
  public actual val purple: Color = Color("purple")

  public actual fun rgb(red: UByte, green: UByte, blue: UByte, alpha: Float?): Color {
    return if (alpha != null) {
      Color("rgba($red, $green, $blue, $alpha)")
    } else {
      Color("rgb($red, $green, $blue)")
    }
  }

  public actual fun hsl(hue: Int, saturation: Int, lightness: Int, alpha: Float?): Color {
    return if (alpha != null) {
      Color("hsla($hue, $saturation%, $lightness%, $alpha)")
    } else {
      Color("hsl($hue, $saturation%, $lightness%)")
    }
  }
}
