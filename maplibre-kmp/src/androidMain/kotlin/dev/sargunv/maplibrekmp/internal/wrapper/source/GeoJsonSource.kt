package dev.sargunv.maplibrekmp.internal.wrapper.source

import dev.sargunv.maplibrekmp.internal.correctedAndroidUri
import org.maplibre.android.style.sources.GeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

internal actual class GeoJsonSource
actual constructor(override val id: String, url: String, tolerance: Float?) : Source() {
  override val impl =
    MLNGeoJsonSource(
      id,
      url.correctedAndroidUri(),
      GeoJsonOptions().apply { tolerance?.let { withTolerance(it) } },
    )
}
