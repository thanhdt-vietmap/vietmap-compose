package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.util.toMLNExpression
import dev.sargunv.maplibrekmp.expression.Expression
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.expressions.Expression as MLNExpression
import org.maplibre.android.style.layers.HeatmapLayer as MLNHeatmapLayer

@PublishedApi
internal actual class HeatmapLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = MLNHeatmapLayer(id, source.id)

  override var sourceLayer: String by impl::sourceLayer

  override fun setFilter(filter: Expression<Boolean>) {
    impl.setFilter(filter.toMLNExpression() ?: MLNExpression.literal(true))
  }

  actual fun setHeatmapRadius(radius: Expression<Number>) {
    impl.setProperties(PropertyFactory.heatmapRadius(radius.toMLNExpression()))
  }

  actual fun setHeatmapWeight(weight: Expression<Number>) {
    impl.setProperties(PropertyFactory.heatmapWeight(weight.toMLNExpression()))
  }

  actual fun setHeatmapIntensity(intensity: Expression<Number>) {
    impl.setProperties(PropertyFactory.heatmapIntensity(intensity.toMLNExpression()))
  }

  actual fun setHeatmapColor(color: Expression<Color>) {
    impl.setProperties(PropertyFactory.heatmapColor(color.toMLNExpression()))
  }

  actual fun setHeatmapOpacity(opacity: Expression<Number>) {
    impl.setProperties(PropertyFactory.heatmapOpacity(opacity.toMLNExpression()))
  }
}
