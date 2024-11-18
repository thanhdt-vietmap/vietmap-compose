package dev.sargunv.maplibrekmp.core.source

import io.github.dellisd.spatialk.geojson.GeoJson

public expect class GeoJsonSource : Source {
  public constructor(id: String, dataUrl: String, options: GeoJsonOptions)

  public constructor(id: String, data: GeoJson, options: GeoJsonOptions)

  public fun setDataUrl(url: String)

  public fun setData(geoJson: GeoJson)
}
