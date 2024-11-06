package dev.sargunv.maplibrekmp.style.source

public sealed class Source {
  public data class GeoJson(val url: String, val tolerance: Float? = null) : Source()
}
