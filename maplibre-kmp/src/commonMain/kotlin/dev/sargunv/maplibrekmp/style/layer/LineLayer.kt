package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.compose.LayerNode
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.wrapper.layer.LineLayer
import dev.sargunv.maplibrekmp.style.IncrementingId
import dev.sargunv.maplibrekmp.style.LayerContainerScope
import dev.sargunv.maplibrekmp.style.SourceHandle
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Expressions.const
import dev.sargunv.maplibrekmp.style.expression.Expressions.nil
import dev.sargunv.maplibrekmp.style.expression.Expressions.point
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.expression.TResolvedImage
import dev.sargunv.maplibrekmp.style.getSource

public data class LineLayout(
  val lineCap: Expression<String> = const("butt"),
  val lineJoin: Expression<String> = const("miter"),
  val lineMiterLimit: Expression<Number> = const(2),
  val lineRoundLimit: Expression<Number> = const(1.05),
  val lineSortKey: Expression<Number> = nil(),
)

public data class LinePaint(
  val lineOpacity: Expression<Number> = const(1),
  val lineColor: Expression<Color> = const(Color.Black),
  val lineTranslate: Expression<Point> = point(0, 0),
  val lineTranslateAnchor: Expression<String> = const("map"),
  val lineWidth: Expression<Number> = const(1),
  val lineGapWidth: Expression<Number> = const(0),
  val lineOffset: Expression<Number> = const(0),
  val lineBlur: Expression<Number> = const(0),
  val lineDasharray: Expression<List<Number>> = nil(),
  val linePattern: Expression<TResolvedImage> = nil(),
  val lineGradient: Expression<Color> = nil(),
)

@Composable
public fun LayerContainerScope.LineLayer(
  source: SourceHandle,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  layout: LineLayout = LineLayout(),
  paint: LinePaint = LinePaint(),
) {
  val id = remember { IncrementingId.next() }
  val s = getSource(source) ?: return
  key(id, s) {
    ComposeNode<LayerNode<LineLayer>, MapNodeApplier>(
      factory = { LayerNode(LineLayer(id = id, source = s)) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(layout.lineCap) { layer.setLineCap(it) }
        set(layout.lineJoin) { layer.setLineJoin(it) }
        set(layout.lineMiterLimit) { layer.setLineMiterLimit(it) }
        set(layout.lineRoundLimit) { layer.setLineRoundLimit(it) }
        set(layout.lineSortKey) { layer.setLineSortKey(it) }
        set(paint.lineOpacity) { layer.setLineOpacity(it) }
        set(paint.lineColor) { layer.setLineColor(it) }
        set(paint.lineTranslate) { layer.setLineTranslate(it) }
        set(paint.lineTranslateAnchor) { layer.setLineTranslateAnchor(it) }
        set(paint.lineWidth) { layer.setLineWidth(it) }
        set(paint.lineGapWidth) { layer.setLineGapWidth(it) }
        set(paint.lineOffset) { layer.setLineOffset(it) }
        set(paint.lineBlur) { layer.setLineBlur(it) }
        set(paint.lineDasharray) { layer.setLineDasharray(it) }
        set(paint.linePattern) { layer.setLinePattern(it) }
        set(paint.lineGradient) { layer.setLineGradient(it) }
      },
    )
  }
}
