package dev.sargunv.maplibrekmp.core.source

import io.github.dellisd.spatialk.geojson.GeoJson

@PublishedApi
internal expect class GeoJsonSource : Source {
  constructor(id: String, dataUrl: String, options: GeoJsonOptions)

  constructor(id: String, data: GeoJson, options: GeoJsonOptions)

  fun setDataUrl(url: String)

  fun setData(geoJson: GeoJson)
}
