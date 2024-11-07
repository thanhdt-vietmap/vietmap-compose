package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNLineStyleLayer
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.style.source.Source

internal actual class LineLayer
actual constructor(override val id: String, override val source: Source) : Layer() {

  override val impl = MLNLineStyleLayer(id, source.impl)

  actual fun setColor(color: Expression<Color>) {
    impl.setLineColor(color.convert())
  }

  actual fun setWidth(width: Expression<Number>) {
    impl.setLineWidth(width.convert())
  }
}
