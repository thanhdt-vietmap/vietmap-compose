package dev.sargunv.maplibrecompose.core.source

import dev.sargunv.maplibrecompose.core.util.correctedAndroidUri
import org.maplibre.android.style.sources.VectorSource

public actual class VectorSource actual constructor(id: String, configUrl: String) : Source() {
  override val impl: VectorSource = VectorSource(id, configUrl.correctedAndroidUri().toString())
}
