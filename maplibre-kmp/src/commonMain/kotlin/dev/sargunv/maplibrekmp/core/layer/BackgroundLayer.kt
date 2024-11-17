package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal expect class BackgroundLayer(id: String, anchor: Anchor) : UserLayer {
  fun setBackgroundColor(backgroundColor: Expression<Color>)

  fun setBackgroundPattern(backgroundPattern: Expression<TResolvedImage>)

  fun setBackgroundOpacity(backgroundOpacity: Expression<Number>)
}
