package dev.sargunv.maplibrekmp.internal.wrapper.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal expect class LineLayer(id: String, source: Source) : Layer {
  fun setColor(color: Expression<Color>)

  fun setWidth(width: Expression<Number>)
}
