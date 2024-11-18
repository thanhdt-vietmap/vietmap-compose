package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.BackgroundLayer
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Expression.Companion.const
import dev.sargunv.maplibrekmp.expression.Expression.Companion.nil
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import androidx.compose.runtime.key as composeKey

public class BackgroundLayout

public data class BackgroundPaint(
  val color: Expression<Color> = const(Color.Black),
  val pattern: Expression<TResolvedImage> = nil(),
  val opacity: Expression<Number> = const(1),
)

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun BackgroundLayer(
  id: String,
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  visible: Boolean = true,
  @Suppress("UNUSED_PARAMETER") layout: BackgroundLayout = BackgroundLayout(),
  paint: BackgroundPaint = BackgroundPaint(),
) {
  composeKey(id) {
    LayerNode(
      factory = { BackgroundLayer(id = id) },
      update = {
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(visible) { layer.visible = it }
        set(paint.color) { layer.setBackgroundColor(it) }
        set(paint.pattern) { layer.setBackgroundPattern(it) }
        set(paint.opacity) { layer.setBackgroundOpacity(it) }
      },
      onClick = null,
      onLongClick = null,
    )
  }
}
