package dev.sargunv.maplibrekmp.style.source

import org.maplibre.android.style.sources.Source as MLNSource

internal actual class NativeSource private actual constructor(override val id: String) : Source() {
  private lateinit var _impl: MLNSource

  constructor(source: MLNSource) : this(source.id) {
    _impl = source
  }

  override val impl
    get() = _impl
}
