package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.CircleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import io.github.dellisd.spatialk.geojson.Feature

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun CircleLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  sortKey: Expression<Number> = nil(),
  radius: Expression<Number> = const(5),
  color: Expression<Color> = const(Color.Black),
  blur: Expression<Number> = const(0),
  opacity: Expression<Number> = const(1),
  translate: Expression<Point> = point(0, 0),
  translateAnchor: Expression<String> = const("map"),
  pitchScale: Expression<String> = const("map"),
  pitchAlignment: Expression<String> = const("viewport"),
  strokeWidth: Expression<Number> = const(0),
  strokeColor: Expression<Color> = const(Color.Black),
  strokeOpacity: Expression<Number> = const(1),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { CircleLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(sortKey) { layer.setCircleSortKey(it) }
        set(radius) { layer.setCircleRadius(it) }
        set(color) { layer.setCircleColor(it) }
        set(blur) { layer.setCircleBlur(it) }
        set(opacity) { layer.setCircleOpacity(it) }
        set(translate) { layer.setCircleTranslate(it) }
        set(translateAnchor) { layer.setCircleTranslateAnchor(it) }
        set(pitchScale) { layer.setCirclePitchScale(it) }
        set(pitchAlignment) { layer.setCirclePitchAlignment(it) }
        set(strokeWidth) { layer.setCircleStrokeWidth(it) }
        set(strokeColor) { layer.setCircleStrokeColor(it) }
        set(strokeOpacity) { layer.setCircleStrokeOpacity(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
