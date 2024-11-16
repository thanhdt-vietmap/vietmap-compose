package dev.sargunv.maplibrekmp.core.source

import io.github.dellisd.spatialk.geojson.GeoJson

@PublishedApi
internal expect class GeoJsonSource(id: String, shape: Shape, options: GeoJsonOptions) :
  UserSource {
  fun setShapeUrl(url: String)

  fun setShape(geoJson: GeoJson)
}
