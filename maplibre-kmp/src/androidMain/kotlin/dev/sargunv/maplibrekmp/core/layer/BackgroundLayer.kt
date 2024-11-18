package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.BackgroundLayer as MLNBackgroundLayer

@PublishedApi
internal actual class BackgroundLayer actual constructor(id: String) : Layer() {

  override val impl: MLNBackgroundLayer = MLNBackgroundLayer(id)

  actual fun setBackgroundColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.backgroundColor(color.toMLNExpression()))
  }

  actual fun setBackgroundPattern(pattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.backgroundPattern(pattern.toMLNExpression()))
  }

  actual fun setBackgroundOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.backgroundOpacity(opacity.toMLNExpression()))
  }
}
