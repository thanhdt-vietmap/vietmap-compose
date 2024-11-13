package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.CircleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import androidx.compose.runtime.key as composeKey

public data class CircleLayout(val sortKey: Expression<Number> = nil())

public data class CirclePaint(
  val radius: Expression<Number> = const(5),
  val color: Expression<Color> = const(Color.Black),
  val blur: Expression<Number> = const(0),
  val opacity: Expression<Number> = const(1),
  val translate: Expression<Point> = point(0, 0),
  val translateAnchor: Expression<String> = const("map"),
  val pitchScale: Expression<String> = const("map"),
  val pitchAlignment: Expression<String> = const("viewport"),
  val strokeWidth: Expression<Number> = const(0),
  val strokeColor: Expression<Color> = const(Color.Black),
  val strokeOpacity: Expression<Number> = const(1),
)

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun CircleLayer(
  key: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  layout: CircleLayout = CircleLayout(),
  paint: CirclePaint = CirclePaint(),
) {
  composeKey(key) {
    LayerNode(
      key = key,
      factory = { id, anchor -> CircleLayer(id = id, source = source, anchor) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(layout.sortKey) { layer.setCircleSortKey(it) }
        set(paint.radius) { layer.setCircleRadius(it) }
        set(paint.color) { layer.setCircleColor(it) }
        set(paint.blur) { layer.setCircleBlur(it) }
        set(paint.opacity) { layer.setCircleOpacity(it) }
        set(paint.translate) { layer.setCircleTranslate(it) }
        set(paint.translateAnchor) { layer.setCircleTranslateAnchor(it) }
        set(paint.pitchScale) { layer.setCirclePitchScale(it) }
        set(paint.pitchAlignment) { layer.setCirclePitchAlignment(it) }
        set(paint.strokeWidth) { layer.setCircleStrokeWidth(it) }
        set(paint.strokeColor) { layer.setCircleStrokeColor(it) }
        set(paint.strokeOpacity) { layer.setCircleStrokeOpacity(it) }
      },
    )
  }
}
