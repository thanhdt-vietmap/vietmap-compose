package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.FillExtrusionLayer
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
public inline fun FillExtrusionLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  opacity: Expression<Number> = const(1.0),
  color: Expression<Color> = const(Color.Black),
  translate: Expression<Point> = point(0.0, 0.0),
  translateAnchor: Expression<String> = const("map"),
  pattern: Expression<TResolvedImage> = nil(),
  height: Expression<Number> = const(0.0),
  base: Expression<Number> = const(0.0),
  verticalGradient: Expression<Boolean> = const(true),
  noinline onClick: ((features: List<Feature>) -> Unit)? = null,
  noinline onLongClick: ((features: List<Feature>) -> Unit)? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { FillExtrusionLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(opacity) { layer.setFillExtrusionOpacity(it) }
        set(color) { layer.setFillExtrusionColor(it) }
        set(translate) { layer.setFillExtrusionTranslate(it) }
        set(translateAnchor) { layer.setFillExtrusionTranslateAnchor(it) }
        set(pattern) { layer.setFillExtrusionPattern(it) }
        set(height) { layer.setFillExtrusionHeight(it) }
        set(base) { layer.setFillExtrusionBase(it) }
        set(verticalGradient) { layer.setFillExtrusionVerticalGradient(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}
