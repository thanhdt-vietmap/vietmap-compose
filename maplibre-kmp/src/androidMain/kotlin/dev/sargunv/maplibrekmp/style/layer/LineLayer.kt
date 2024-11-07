package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.style.expression.Expression
import dev.sargunv.maplibrekmp.style.expression.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.style.source.Source
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.PropertyFactory

internal actual class LineLayer
actual constructor(override val id: String, override val source: Source) : Layer() {

  override val impl = LineLayer(id, source.id)

  actual fun setColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.lineColor(color.convert()))
  }

  actual fun setWidth(width: Expression<Number>) {
    impl.setProperties(PropertyFactory.lineWidth(width.convert()))
  }
}
