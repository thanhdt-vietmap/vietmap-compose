package dev.sargunv.vietmapcompose.core.util

import android.graphics.PointF
import android.graphics.RectF
import android.view.Gravity
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import dev.sargunv.vietmapcompose.expressions.ast.BooleanLiteral
import dev.sargunv.vietmapcompose.expressions.ast.ColorLiteral
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.ast.CompiledFunctionCall
import dev.sargunv.vietmapcompose.expressions.ast.CompiledListLiteral
import dev.sargunv.vietmapcompose.expressions.ast.CompiledMapLiteral
import dev.sargunv.vietmapcompose.expressions.ast.CompiledOptions
import dev.sargunv.vietmapcompose.expressions.ast.DpPaddingLiteral
import dev.sargunv.vietmapcompose.expressions.ast.FloatLiteral
import dev.sargunv.vietmapcompose.expressions.ast.NullLiteral
import dev.sargunv.vietmapcompose.expressions.ast.OffsetLiteral
import dev.sargunv.vietmapcompose.expressions.ast.StringLiteral
import io.github.dellisd.spatialk.geojson.BoundingBox
import io.github.dellisd.spatialk.geojson.Position
import java.net.URI
import java.net.URISyntaxException
import vn.vietmap.vietmapsdk.geometry.LatLng
import vn.vietmap.vietmapsdk.geometry.LatLngBounds
import vn.vietmap.vietmapsdk.style.expressions.Expression as MLNExpression

internal fun String.correctedAndroidUri(): String {
  return try {
    val uri = URI(this)
    if (uri.scheme == "file" && uri.path.startsWith("/android_asset/"))
      URI("asset://${uri.path.removePrefix("/android_asset/")}").toString()
    else this
  } catch (_: URISyntaxException) {
    this
  }
}

internal fun DpOffset.toPointF(density: Density): PointF =
  with(density) { PointF(x.toPx(), y.toPx()) }

internal fun PointF.toOffset(density: Density): DpOffset =
  with(density) { DpOffset(x = x.toDp(), y = y.toDp()) }

internal fun DpRect.toRectF(density: Density): RectF =
  with(density) { RectF(left.toPx(), top.toPx(), right.toPx(), bottom.toPx()) }

internal fun LatLng.toPosition(): Position = Position(longitude = longitude, latitude = latitude)

internal fun Position.toLatLng(): LatLng = LatLng(latitude = latitude, longitude = longitude)

internal fun LatLngBounds.toBoundingBox(): BoundingBox =
  BoundingBox(northeast = northEast.toPosition(), southwest = southWest.toPosition())

internal fun BoundingBox.toLatLngBounds(): LatLngBounds =
  LatLngBounds.from(
    latNorth = northeast.latitude,
    lonEast = northeast.longitude,
    latSouth = southwest.latitude,
    lonWest = southwest.longitude,
  )

internal fun CompiledExpression<*>.toMLNExpression(): MLNExpression? =
  if (this == NullLiteral) null else MLNExpression.Converter.convert(normalizeJsonLike(false))

private fun buildLiteralArray(inLiteral: Boolean, block: JsonArray.() -> Unit): JsonArray {
  return if (inLiteral) {
    JsonArray().apply(block)
  } else {
    JsonArray(2).apply {
      add("literal")
      add(JsonArray().apply(block))
    }
  }
}

private fun buildLiteralObject(inLiteral: Boolean, block: JsonObject.() -> Unit): JsonObject {
  return if (inLiteral) {
    JsonObject().apply(block)
  } else {
    JsonObject().apply { add("literal", JsonObject().apply(block)) }
  }
}

private fun CompiledExpression<*>.normalizeJsonLike(inLiteral: Boolean): JsonElement =
  when (this) {
    NullLiteral -> JsonNull.INSTANCE
    is BooleanLiteral -> JsonPrimitive(value)
    is FloatLiteral -> JsonPrimitive(value)
    is StringLiteral -> JsonPrimitive(value)
    is OffsetLiteral ->
      buildLiteralArray(inLiteral) {
        add(value.x)
        add(value.y)
      }

    is ColorLiteral ->
      JsonPrimitive(
        value.toArgb().let {
          "rgba(${(it shr 16) and 0xFF}, ${(it shr 8) and 0xFF}, ${it and 0xFF}, ${value.alpha})"
        }
      )

    is DpPaddingLiteral ->
      buildLiteralArray(inLiteral) {
        add(value.calculateTopPadding().value)
        add(value.calculateRightPadding(LayoutDirection.Ltr).value)
        add(value.calculateBottomPadding().value)
        add(value.calculateLeftPadding(LayoutDirection.Ltr).value)
      }

    is CompiledFunctionCall ->
      JsonArray(args.size + 1).apply {
        add(name)
        args.forEachIndexed { i, v -> add(v.normalizeJsonLike(inLiteral || isLiteralArg(i))) }
      }

    is CompiledListLiteral<*> ->
      buildLiteralArray(inLiteral) { value.forEach { add(it.normalizeJsonLike(true)) } }

    is CompiledMapLiteral<*> ->
      buildLiteralObject(inLiteral) {
        value.forEach { (k, v) -> add(k, v.normalizeJsonLike(true)) }
      }

    is CompiledOptions<*> ->
      JsonObject().apply { value.forEach { (k, v) -> add(k, v.normalizeJsonLike(inLiteral)) } }
  }

internal fun Alignment.toGravity(layoutDir: LayoutDirection): Int {
  val (x, y) = align(IntSize(1, 1), IntSize(3, 3), layoutDir)
  val h =
    when (x) {
      0 -> Gravity.LEFT
      1 -> Gravity.CENTER_HORIZONTAL
      2 -> Gravity.RIGHT
      else -> error("Invalid alignment")
    }
  val v =
    when (y) {
      0 -> Gravity.TOP
      1 -> Gravity.CENTER_VERTICAL
      2 -> Gravity.BOTTOM
      else -> error("Invalid alignment")
    }
  return h or v
}
