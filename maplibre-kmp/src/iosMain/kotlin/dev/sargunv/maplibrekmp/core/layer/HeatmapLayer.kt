package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import cocoapods.MapLibre.MLNHeatmapStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toNSExpression
import dev.sargunv.maplibrekmp.core.util.toPredicate
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal actual class HeatmapLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {

  override val impl = MLNHeatmapStyleLayer(id, source.impl)

  override var sourceLayer: String
    get() = impl.sourceLayerIdentifier!!
    set(value) {
      impl.sourceLayerIdentifier = value
    }

  override fun setFilter(filter: Expression<Boolean>) {
    impl.predicate = filter.toPredicate()
  }

  actual fun setHeatmapRadius(radius: Expression<Number>) {
    impl.heatmapRadius = radius.toNSExpression()
  }

  actual fun setHeatmapWeight(weight: Expression<Number>) {
    impl.heatmapWeight = weight.toNSExpression()
  }

  actual fun setHeatmapIntensity(intensity: Expression<Number>) {
    impl.heatmapIntensity = intensity.toNSExpression()
  }

  actual fun setHeatmapColor(color: Expression<Color>) {
    impl.heatmapColor = color.toNSExpression()
  }

  actual fun setHeatmapOpacity(opacity: Expression<Number>) {
    impl.heatmapOpacity = opacity.toNSExpression()
  }
}
