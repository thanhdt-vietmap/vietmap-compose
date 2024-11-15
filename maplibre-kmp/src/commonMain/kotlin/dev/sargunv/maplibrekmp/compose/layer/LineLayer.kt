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

public data class LineLayout(
  val cap: Expression<String> = const("butt"),
  val join: Expression<String> = const("miter"),
  val miterLimit: Expression<Number> = const(2),
  val roundLimit: Expression<Number> = const(1.05),
  val sortKey: Expression<Number> = nil(),
)

public data class LinePaint(
  val opacity: Expression<Number> = const(1),
  val color: Expression<Color> = const(Color.Black),
  val translate: Expression<Point> = point(0, 0),
  val translateAnchor: Expression<String> = const("map"),
  val width: Expression<Number> = const(1),
  val gapWidth: Expression<Number> = const(0),
  val offset: Expression<Number> = const(0),
  val blur: Expression<Number> = const(0),
  val dasharray: Expression<List<Number>> = nil(),
  val pattern: Expression<TResolvedImage> = nil(),
  val gradient: Expression<Color> = nil(),
)

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
  layout: LineLayout = LineLayout(),
  paint: LinePaint = LinePaint(),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { anchor -> LineLayer(id = id, source = source, anchor = anchor) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(layout.cap) { layer.setLineCap(it) }
        set(layout.join) { layer.setLineJoin(it) }
        set(layout.miterLimit) { layer.setLineMiterLimit(it) }
        set(layout.roundLimit) { layer.setLineRoundLimit(it) }
        set(layout.sortKey) { layer.setLineSortKey(it) }
        set(paint.opacity) { layer.setLineOpacity(it) }
        set(paint.color) { layer.setLineColor(it) }
        set(paint.translate) { layer.setLineTranslate(it) }
        set(paint.translateAnchor) { layer.setLineTranslateAnchor(it) }
        set(paint.width) { layer.setLineWidth(it) }
        set(paint.gapWidth) { layer.setLineGapWidth(it) }
        set(paint.offset) { layer.setLineOffset(it) }
        set(paint.blur) { layer.setLineBlur(it) }
        set(paint.dasharray) { layer.setLineDasharray(it) }
        set(paint.pattern) { layer.setLinePattern(it) }
        set(paint.gradient) { layer.setLineGradient(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
