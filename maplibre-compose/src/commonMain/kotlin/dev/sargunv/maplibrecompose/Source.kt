package dev.sargunv.maplibrecompose

public sealed class Source {
  public data class GeoJson(val url: String, val tolerance: Float? = null) : Source()
}
