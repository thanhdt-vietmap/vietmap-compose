package dev.sargunv.maplibrecompose.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrecompose.core.expression.Expression
import dev.sargunv.maplibrecompose.core.expression.TResolvedImage

@PublishedApi
internal expect class BackgroundLayer(id: String) : Layer {
  fun setBackgroundColor(color: Expression<Color>)

  fun setBackgroundPattern(pattern: Expression<TResolvedImage>)

  fun setBackgroundOpacity(opacity: Expression<Number>)
}
