package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.BackgroundLayer
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun BackgroundLayer(
  id: String,
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  visible: Boolean = true,
  color: Expression<Color> = const(Color.Black),
  pattern: Expression<TResolvedImage> = nil(),
  opacity: Expression<Number> = const(1),
) {
  composeKey(id) {
    LayerNode(
      factory = { BackgroundLayer(id = id) },
      update = {
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(visible) { layer.visible = it }
        set(color) { layer.setBackgroundColor(it) }
        set(pattern) { layer.setBackgroundPattern(it) }
        set(opacity) { layer.setBackgroundOpacity(it) }
      },
      onClick = null,
      onLongClick = null,
    )
  }
}
