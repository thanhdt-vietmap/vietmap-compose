package dev.sargunv.maplibrecompose.core.layer

import dev.sargunv.maplibrecompose.core.source.Source
import dev.sargunv.maplibrecompose.core.util.toMLNExpression
import dev.sargunv.maplibrecompose.expressions.ast.CompiledExpression
import dev.sargunv.maplibrecompose.expressions.value.ColorValue
import dev.sargunv.maplibrecompose.expressions.value.FloatValue
import dev.sargunv.maplibrecompose.expressions.value.IlluminationAnchor
import vn.vietmap.vietmapsdk.style.layers.HillshadeLayer as MLNHillshadeLayer
import vn.vietmap.vietmapsdk.style.layers.PropertyFactory

internal actual class HillshadeLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = MLNHillshadeLayer(id, source.id)

  actual fun setHillshadeIlluminationDirection(direction: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.hillshadeIlluminationDirection(direction.toMLNExpression()))
  }

  actual fun setHillshadeIlluminationAnchor(anchor: CompiledExpression<IlluminationAnchor>) {
    impl.setProperties(PropertyFactory.hillshadeIlluminationAnchor(anchor.toMLNExpression()))
  }

  actual fun setHillshadeExaggeration(exaggeration: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.hillshadeExaggeration(exaggeration.toMLNExpression()))
  }

  actual fun setHillshadeShadowColor(shadowColor: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.hillshadeShadowColor(shadowColor.toMLNExpression()))
  }

  actual fun setHillshadeHighlightColor(highlightColor: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.hillshadeHighlightColor(highlightColor.toMLNExpression()))
  }

  actual fun setHillshadeAccentColor(accentColor: CompiledExpression<ColorValue>) {
    impl.setProperties(PropertyFactory.hillshadeAccentColor(accentColor.toMLNExpression()))
  }
}
