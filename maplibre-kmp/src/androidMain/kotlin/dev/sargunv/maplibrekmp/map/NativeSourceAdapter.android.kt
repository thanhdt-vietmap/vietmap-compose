package dev.sargunv.maplibrekmp.map

import dev.sargunv.maplibrekmp.style.Source
import org.maplibre.android.style.sources.GeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource
import org.maplibre.android.style.sources.Source as NativeSource

internal object NativeSourceAdapter : StyleManager.Adapter<Source, NativeSource> {
  private fun Source.GeoJson.buildNativeSource(): GeoJsonSource {
    return GeoJsonSource(
      id = id,
      uri = url.correctedAndroidUri(),
      options = GeoJsonOptions().apply { tolerance?.let { withTolerance(it) } },
    )
  }

  override fun convert(common: Source): NativeSource {
    return when (common) {
      is Source.GeoJson -> common.buildNativeSource()
    }
  }
}
