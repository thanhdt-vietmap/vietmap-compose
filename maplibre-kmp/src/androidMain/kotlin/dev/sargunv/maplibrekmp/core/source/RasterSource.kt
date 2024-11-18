package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.util.correctedAndroidUri
import org.maplibre.android.style.sources.RasterSource

public actual class RasterSource actual constructor(id: String, configUrl: String) : Source() {
  override val impl: RasterSource = RasterSource(id, configUrl.correctedAndroidUri().toString())
}
