package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect class HeatmapLayer(id: String, source: Source) : FeatureLayer {
  fun setHeatmapRadius(expression: Expression<Number>)

  fun setHeatmapWeight(expression: Expression<Number>)

  fun setHeatmapIntensity(expression: Expression<Number>)

  fun setHeatmapColor(expression: Expression<Color>)

  fun setHeatmapOpacity(expression: Expression<Number>)
}

