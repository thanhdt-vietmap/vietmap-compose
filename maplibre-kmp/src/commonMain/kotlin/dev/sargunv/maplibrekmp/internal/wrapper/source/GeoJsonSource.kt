package dev.sargunv.maplibrekmp.internal.wrapper.source

import dev.sargunv.maplibrekmp.style.source.GeoJsonOptions

internal expect class GeoJsonSource(
  id: String,
  dataUrl: String? = null,
  dataJson: String? = null,
  options: GeoJsonOptions,
) : Source {
  fun setDataUrl(url: String)

  fun setDataJson(json: String)
}
