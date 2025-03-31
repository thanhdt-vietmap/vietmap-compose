package vn.vietmap.vietmapcompose.core.layer

import cocoapods.VietMap.MLNHeatmapStyleLayer
import vn.vietmap.vietmapcompose.core.source.Source
import vn.vietmap.VietMapcompose.core.util.toNSExpression
import vn.vietmap.VietMapcompose.core.util.toNSPredicate
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.ColorValue
import vn.vietmap.vietmapcompose.expressions.value.DpValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue

internal actual class HeatmapLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNHeatmapStyleLayer(id, source.impl)

  actual override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    impl.predicate = filter.toNSPredicate()
  }

  actual fun setHeatmapRadius(radius: CompiledExpression<DpValue>) {
    impl.heatmapRadius = radius.toNSExpression()
  }

  actual fun setHeatmapWeight(weight: CompiledExpression<FloatValue>) {
    impl.heatmapWeight = weight.toNSExpression()
  }

  actual fun setHeatmapIntensity(intensity: CompiledExpression<FloatValue>) {
    impl.heatmapIntensity = intensity.toNSExpression()
  }

  actual fun setHeatmapColor(color: CompiledExpression<ColorValue>) {
    impl.heatmapColor = color.toNSExpression()
  }

  actual fun setHeatmapOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.heatmapOpacity = opacity.toNSExpression()
  }
}
