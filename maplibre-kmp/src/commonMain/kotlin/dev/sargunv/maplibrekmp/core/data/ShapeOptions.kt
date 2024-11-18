package dev.sargunv.maplibrekmp.core.data

import androidx.compose.runtime.Immutable

@Immutable
public sealed class ShapeOptions {
  public data class Url(val url: String) : ShapeOptions()

  public data class GeoJson(val geoJson: io.github.dellisd.spatialk.geojson.GeoJson) : ShapeOptions()
}
