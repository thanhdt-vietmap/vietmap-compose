package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.FillLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.Expression.Companion.point
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import io.github.dellisd.spatialk.geojson.Feature
import androidx.compose.runtime.key as composeKey

public data class FillLayout(val sortKey: Expression<Number> = nil())

public data class FillPaint(
  val antialias: Expression<Boolean> = const(true),
  val opacity: Expression<Number> = const(1.0),
  val color: Expression<Color> = const(Color.Black),
  val outlineColor: Expression<Color> = color,
  val translate: Expression<Point> = point(0.0, 0.0),
  val translateAnchor: Expression<String> = const("map"),
  val pattern: Expression<TResolvedImage> = nil(),
)

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun FillLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  layout: FillLayout = FillLayout(),
  paint: FillPaint = FillPaint(),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { FillLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(paint.antialias) { layer.setFillAntialias(it) }
        set(paint.opacity) { layer.setFillOpacity(it) }
        set(paint.color) { layer.setFillColor(it) }
        set(paint.outlineColor) { layer.setFillOutlineColor(it) }
        set(paint.translate) { layer.setFillTranslate(it) }
        set(paint.translateAnchor) { layer.setFillTranslateAnchor(it) }
        set(paint.pattern) { layer.setFillPattern(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
