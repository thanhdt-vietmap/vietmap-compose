package vn.vietmap.vietmapcompose.core.layer

import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue

internal actual class HeatmapLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setHeatmapRadius(radius: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setHeatmapWeight(weight: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setHeatmapIntensity(intensity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setHeatmapColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setHeatmapOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }
}
