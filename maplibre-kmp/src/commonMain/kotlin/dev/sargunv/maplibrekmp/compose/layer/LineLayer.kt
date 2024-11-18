package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.LineLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun LineLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  cap: Expression<String> = const("butt"),
  join: Expression<String> = const("miter"),
  miterLimit: Expression<Number> = const(2),
  roundLimit: Expression<Number> = const(1.05),
  sortKey: Expression<Number> = nil(),
  opacity: Expression<Number> = const(1),
  color: Expression<Color> = const(Color.Black),
  translate: Expression<Point> = point(0, 0),
  translateAnchor: Expression<String> = const("map"),
  width: Expression<Number> = const(1),
  gapWidth: Expression<Number> = const(0),
  offset: Expression<Number> = const(0),
  blur: Expression<Number> = const(0),
  dasharray: Expression<List<Number>> = nil(),
  pattern: Expression<TResolvedImage> = nil(),
  gradient: Expression<Color> = nil(),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { LineLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(cap) { layer.setLineCap(it) }
        set(join) { layer.setLineJoin(it) }
        set(miterLimit) { layer.setLineMiterLimit(it) }
        set(roundLimit) { layer.setLineRoundLimit(it) }
        set(sortKey) { layer.setLineSortKey(it) }
        set(opacity) { layer.setLineOpacity(it) }
        set(color) { layer.setLineColor(it) }
        set(translate) { layer.setLineTranslate(it) }
        set(translateAnchor) { layer.setLineTranslateAnchor(it) }
        set(width) { layer.setLineWidth(it) }
        set(gapWidth) { layer.setLineGapWidth(it) }
        set(offset) { layer.setLineOffset(it) }
        set(blur) { layer.setLineBlur(it) }
        set(dasharray) { layer.setLineDasharray(it) }
        set(pattern) { layer.setLinePattern(it) }
        set(gradient) { layer.setLineGradient(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
