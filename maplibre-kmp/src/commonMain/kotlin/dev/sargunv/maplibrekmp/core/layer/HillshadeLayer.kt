package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect class HillshadeLayer(id: String, source: Source) : Layer {
  val source: Source

  fun setHillshadeIlluminationDirection(direction: Expression<Number>)

  fun setHillshadeIlluminationAnchor(anchor: Expression<String>)

  fun setHillshadeExaggeration(exaggeration: Expression<Number>)

  fun setHillshadeShadowColor(shadowColor: Expression<Color>)

  fun setHillshadeHighlightColor(highlightColor: Expression<Color>)

  fun setHillshadeAccentColor(accentColor: Expression<Color>)
}
