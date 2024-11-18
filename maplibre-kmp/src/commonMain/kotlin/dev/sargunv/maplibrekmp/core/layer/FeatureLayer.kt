package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression

@PublishedApi
internal expect sealed class FeatureLayer(source: Source) : Layer {
  val source: Source
  abstract var sourceLayer: String

  abstract fun setFilter(filter: Expression<Boolean>)
}
