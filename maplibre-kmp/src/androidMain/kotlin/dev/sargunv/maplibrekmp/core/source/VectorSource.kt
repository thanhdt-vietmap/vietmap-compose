package dev.sargunv.maplibrekmp.core.source

import dev.sargunv.maplibrekmp.core.util.correctedAndroidUri
import org.maplibre.android.style.sources.VectorSource

public actual class VectorSource actual constructor(id: String, configUrl: String) : Source() {
  override val impl: VectorSource = VectorSource(id, configUrl.correctedAndroidUri().toString())
}
