package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.wrapper.layer.LineLayer
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Expressions.const
import dev.sargunv.maplibrekmp.style.expression.Expressions.nil
import dev.sargunv.maplibrekmp.style.expression.Expressions.point
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.expression.TResolvedImage
import dev.sargunv.maplibrekmp.style.source.SourceHandle

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
public inline fun LineLayer(
  key: String,
  source: SourceHandle,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  layout: LineLayout = LineLayout(),
  paint: LinePaint = LinePaint(),
) {
  composeKey(key) {
    LayerNode(
      key = key,
      factory = { id -> LineLayer(id = id, source = source.source) },
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
    )
  }
}
