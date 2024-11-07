package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.source.Source

internal expect class LineLayer(id: String, source: Source) : Layer {
  fun setColor(color: Expression<Color>)

  fun setWidth(width: Expression<Number>)
}
