package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNBackgroundStyleLayer
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.TResolvedImage

@PublishedApi
internal actual class BackgroundLayer actual constructor(id: String) : Layer() {
  override val impl = MLNBackgroundStyleLayer(id)

  actual fun setBackgroundColor(backgroundColor: Expression<Color>) {
    impl.backgroundColor = backgroundColor.toNSExpression()
  }

  actual fun setBackgroundPattern(backgroundPattern: Expression<TResolvedImage>) {
    // TODO: figure out how to unset a pattern in iOS
    if (backgroundPattern.value != null) {
      impl.backgroundPattern = backgroundPattern.toNSExpression()
    }
  }

  actual fun setBackgroundOpacity(backgroundOpacity: Expression<Number>) {
    impl.backgroundOpacity = backgroundOpacity.toNSExpression()
  }
}
