package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.Source
import org.maplibre.android.style.sources.GeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.android.style.sources.Source as MlnSource

internal fun Source.GeoJson.toNativeSource(): GeoJsonSource {
  return GeoJsonSource(
    id = id,
    uri = url.correctedAndroidUri(),
    options = GeoJsonOptions().apply { tolerance?.let { withTolerance(it) } },
  )
}

internal fun Source.toNativeSource(): MlnSource {
  return when (this) {
    is Source.GeoJson -> toNativeSource()
  }
}
