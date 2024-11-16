package dev.sargunv.maplibrekmp.core.source

import androidx.compose.runtime.Immutable

@Immutable
public sealed class Shape {
  public data class Url(val url: String) : Shape()

  public data class GeoJson(val geoJson: io.github.dellisd.spatialk.geojson.GeoJson) : Shape()
}
