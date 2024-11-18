package dev.sargunv.maplibrekmp.core.data

import androidx.compose.runtime.Immutable

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
