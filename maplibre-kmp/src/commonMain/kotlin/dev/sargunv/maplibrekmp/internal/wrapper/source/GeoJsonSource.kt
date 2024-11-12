package dev.sargunv.maplibrekmp.internal.wrapper.source

import androidx.compose.runtime.Immutable

@PublishedApi
internal expect class GeoJsonSource(id: String, shape: Shape, options: GeoJsonOptions) : Source {
  fun setDataUrl(url: String)

  fun setDataJson(json: String)
}

@Immutable
public data class GeoJsonOptions(
  val maxZoom: Int = 18,
  val buffer: Int = 128,
  val tolerance: Float = 0.375f,
  val cluster: Boolean = false,
  val clusterRadius: Int = 50,
  val clusterMaxZoom: Int = maxZoom - 1,
  val clusterProperties: Map<String, ClusterProperty> = emptyMap(),
  val lineMetrics: Boolean = false,
)

@Immutable
public sealed class Shape {
  public data class Url(val url: String) : Shape()

  public data class GeoJson(val json: String) : Shape()
}
