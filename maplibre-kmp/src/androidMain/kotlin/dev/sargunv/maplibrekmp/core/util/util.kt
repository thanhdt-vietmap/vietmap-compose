package dev.sargunv.maplibrekmp.core.util

import android.graphics.PointF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.sargunv.maplibrekmp.core.data.XY
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import io.github.dellisd.spatialk.geojson.Position
import org.maplibre.android.geometry.LatLng
import java.net.URI
import org.maplibre.android.style.expressions.Expression as MLNExpression

internal fun String.correctedAndroidUri(): URI {
  val uri = URI(this)
  return if (uri.scheme == "file" && uri.path.startsWith("/android_asset/")) {
    URI("asset://${uri.path.removePrefix("/android_asset/")}")
  } else {
    uri
  }
}

internal fun XY.toPointF(): PointF = PointF(x, y)

internal fun PointF.toXY(): XY = XY(x = x, y = y)

internal fun LatLng.toPosition(): Position = Position(longitude = longitude, latitude = latitude)

internal fun Position.toLatLng(): LatLng = LatLng(latitude = latitude, longitude = longitude)

internal fun Expression<*>.toMLNExpression(): MLNExpression? =
  when (value) {
    null -> null
    else -> MLNExpression.Converter.convert(normalizeJsonLike(value))
  }

private fun normalizeJsonLike(value: Any?): JsonElement =
  when (value) {
    null -> JsonNull.INSTANCE
    is Boolean -> JsonPrimitive(value)
    is Number -> JsonPrimitive(value)
    is String -> JsonPrimitive(value)
    is List<*> -> JsonArray().apply { value.forEach { add(normalizeJsonLike(it)) } }
    is Map<*, *> ->
      JsonObject().apply { value.forEach { add(it.key as String, normalizeJsonLike(it.value)) } }
    is Point ->
      JsonArray().apply {
        add("literal")
        add(
          JsonArray().apply {
            add(value.x)
            add(value.y)
          }
        )
      }
    is Color ->
      JsonPrimitive(
        value.toArgb().let {
          "rgba(${(it shr 16) and 0xFF}, ${(it shr 8) and 0xFF}, ${it and 0xFF}, ${value.alpha})"
        }
      )
    else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
  }
