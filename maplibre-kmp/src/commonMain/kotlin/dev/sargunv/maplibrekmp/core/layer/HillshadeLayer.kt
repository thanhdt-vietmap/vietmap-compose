package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect class HillshadeLayer(id: String, source: Source) : Layer {
  fun setHillshadeIlluminationDirection(expression: Expression<Number>)

  fun setHillshadeIlluminationAnchor(expression: Expression<String>)

  fun setHillshadeExaggeration(expression: Expression<Number>)

  fun setHillshadeShadowColor(expression: Expression<Color>)

  fun setHillshadeHighlightColor(expression: Expression<Color>)

  fun setHillshadeAccentColor(expression: Expression<Color>)
}
