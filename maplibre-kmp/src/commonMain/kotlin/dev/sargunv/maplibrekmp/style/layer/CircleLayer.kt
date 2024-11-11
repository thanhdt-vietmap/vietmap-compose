package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.internal.compose.LayerNode
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.wrapper.layer.CircleLayer
import dev.sargunv.maplibrekmp.style.IncrementingId
import dev.sargunv.maplibrekmp.style.LayerContainerScope
import dev.sargunv.maplibrekmp.style.SourceHandle
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.Expressions.const
import dev.sargunv.maplibrekmp.style.expression.Expressions.nil
import dev.sargunv.maplibrekmp.style.expression.Expressions.point
import dev.sargunv.maplibrekmp.style.expression.Point
import dev.sargunv.maplibrekmp.style.getSource

public data class CircleLayout(val circleSortKey: Expression<Number> = nil())

public data class CirclePaint(
  val circleRadius: Expression<Number> = const(5),
  val circleColor: Expression<Color> = const(Color.Black),
  val circleBlur: Expression<Number> = const(0),
  val circleOpacity: Expression<Number> = const(1),
  val circleTranslate: Expression<Point> = point(0, 0),
  val circleTranslateAnchor: Expression<String> = const("map"),
  val circlePitchScale: Expression<String> = const("map"),
  val circlePitchAlignment: Expression<String> = const("viewport"),
  val circleStrokeWidth: Expression<Number> = const(0),
  val circleStrokeColor: Expression<Color> = const(Color.Black),
  val circleStrokeOpacity: Expression<Number> = const(1),
)

@Composable
public fun LayerContainerScope.CircleLayer(
  source: SourceHandle,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  layout: CircleLayout = CircleLayout(),
  paint: CirclePaint = CirclePaint(),
) {
  val id = remember { IncrementingId.next() }
  val s = getSource(source) ?: return
  key(id, s) {
    ComposeNode<LayerNode<CircleLayer>, MapNodeApplier>(
      factory = { LayerNode(CircleLayer(id = id, source = s)) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(layout.circleSortKey) { layer.setCircleSortKey(it) }
        set(paint.circleRadius) { layer.setCircleRadius(it) }
        set(paint.circleColor) { layer.setCircleColor(it) }
        set(paint.circleBlur) { layer.setCircleBlur(it) }
        set(paint.circleOpacity) { layer.setCircleOpacity(it) }
        set(paint.circleTranslate) { layer.setCircleTranslate(it) }
        set(paint.circleTranslateAnchor) { layer.setCircleTranslateAnchor(it) }
        set(paint.circlePitchScale) { layer.setCirclePitchScale(it) }
        set(paint.circlePitchAlignment) { layer.setCirclePitchAlignment(it) }
        set(paint.circleStrokeWidth) { layer.setCircleStrokeWidth(it) }
        set(paint.circleStrokeColor) { layer.setCircleStrokeColor(it) }
        set(paint.circleStrokeOpacity) { layer.setCircleStrokeOpacity(it) }
      },
    )
  }
}
