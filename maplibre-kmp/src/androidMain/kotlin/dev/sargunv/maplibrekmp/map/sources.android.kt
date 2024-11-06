package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.source.Source
import org.maplibre.android.style.sources.GeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource

internal fun Source.GeoJson.toNativeSource(id: String): GeoJsonSource {
  return GeoJsonSource(
    id = id,
    uri = url.correctedAndroidUri(),
    options = GeoJsonOptions().apply { tolerance?.let { withTolerance(it) } },
  )
}
