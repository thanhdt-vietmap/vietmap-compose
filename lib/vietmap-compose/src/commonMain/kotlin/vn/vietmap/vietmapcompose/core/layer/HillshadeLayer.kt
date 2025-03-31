package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.IlluminationAnchor

internal expect class HillshadeLayer(id: String, source: Source) : Layer {
  val source: Source

  fun setHillshadeIlluminationDirection(direction: CompiledExpression<FloatValue>)

  fun setHillshadeIlluminationAnchor(anchor: CompiledExpression<IlluminationAnchor>)

  fun setHillshadeExaggeration(exaggeration: CompiledExpression<FloatValue>)

  fun setHillshadeShadowColor(shadowColor: CompiledExpression<ColorValue>)

  fun setHillshadeHighlightColor(highlightColor: CompiledExpression<ColorValue>)

  fun setHillshadeAccentColor(accentColor: CompiledExpression<ColorValue>)
}
