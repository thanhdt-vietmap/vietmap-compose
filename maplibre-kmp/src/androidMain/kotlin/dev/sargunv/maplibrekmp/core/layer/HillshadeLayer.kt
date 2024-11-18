package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.HillshadeLayer as MLNHillshadeLayer

@PublishedApi
internal actual class HillshadeLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = MLNHillshadeLayer(id, source.id)

  actual fun setHillshadeIlluminationDirection(direction: Expression<Number>) {
    impl.setProperties(PropertyFactory.hillshadeIlluminationDirection(direction.toMLNExpression()))
  }

  actual fun setHillshadeIlluminationAnchor(anchor: Expression<String>) {
    impl.setProperties(PropertyFactory.hillshadeIlluminationAnchor(anchor.toMLNExpression()))
  }

  actual fun setHillshadeExaggeration(exaggeration: Expression<Number>) {
    impl.setProperties(PropertyFactory.hillshadeExaggeration(exaggeration.toMLNExpression()))
  }

  actual fun setHillshadeShadowColor(shadowColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.hillshadeShadowColor(shadowColor.toMLNExpression()))
  }

  actual fun setHillshadeHighlightColor(highlightColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.hillshadeHighlightColor(highlightColor.toMLNExpression()))
  }

  actual fun setHillshadeAccentColor(accentColor: Expression<Color>) {
    impl.setProperties(PropertyFactory.hillshadeAccentColor(accentColor.toMLNExpression()))
  }
}
