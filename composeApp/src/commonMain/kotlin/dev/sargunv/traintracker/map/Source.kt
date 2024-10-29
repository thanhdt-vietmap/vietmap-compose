package dev.sargunv.traintracker.map

sealed class Source {
  data class GeoJson(
      val url: String,
      val tolerance: Float? = null,
  ) : Source()
}
