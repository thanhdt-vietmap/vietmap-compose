package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.layer.ExpressionAdapter.convert
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.TResolvedImage
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.BackgroundLayer as MLNBackgroundLayer

@PublishedApi
internal actual class BackgroundLayer actual constructor(id: String, anchor: Anchor) :
  UserLayer(anchor) {

  override val impl: MLNBackgroundLayer = MLNBackgroundLayer(id)

  actual fun setBackgroundColor(backgroundColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.backgroundColor(backgroundColor.convert()))
  }

  actual fun setBackgroundPattern(backgroundPattern: Expression<TResolvedImage>) {
    impl.setProperties(PropertyFactory.backgroundPattern(backgroundPattern.convert()))
  }

  actual fun setBackgroundOpacity(backgroundOpacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.backgroundOpacity(backgroundOpacity.convert()))
  }
}
