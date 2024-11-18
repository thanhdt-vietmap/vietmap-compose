package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.data.GeoJsonOptions
import dev.sargunv.maplibrekmp.core.data.ShapeOptions
import io.github.dellisd.spatialk.geojson.GeoJson

@PublishedApi
internal expect class GeoJsonSource(id: String, shape: ShapeOptions, options: GeoJsonOptions) : Source {
  fun setShapeUrl(url: String)

  fun setShape(geoJson: GeoJson)
}
