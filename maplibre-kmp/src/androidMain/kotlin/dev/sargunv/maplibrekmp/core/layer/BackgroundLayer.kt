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

  actual fun setBackgroundColor(backgroundColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.backgroundColor(backgroundColor.toMLNExpression()))
  }

  actual fun setBackgroundPattern(backgroundPattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.backgroundPattern(backgroundPattern.toMLNExpression()))
  }

  actual fun setBackgroundOpacity(backgroundOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.backgroundOpacity(backgroundOpacity.toMLNExpression()))
  }
}
