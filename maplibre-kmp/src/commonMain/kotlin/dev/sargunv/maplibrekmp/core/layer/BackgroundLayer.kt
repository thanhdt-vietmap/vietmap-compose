package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class BackgroundLayer(id: String) : Layer {
  fun setBackgroundColor(color: Expression<Color>)

  fun setBackgroundPattern(pattern: Expression<TResolvedImage>)

  fun setBackgroundOpacity(opacity: Expression<Number>)
}
