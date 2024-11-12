package dev.sargunv.maplibrekmp.core.source

@PublishedApi
internal expect class GeoJsonSource(id: String, shape: Shape, options: GeoJsonOptions) : Source {
  fun setDataUrl(url: String)

  fun setDataJson(json: String)
}

