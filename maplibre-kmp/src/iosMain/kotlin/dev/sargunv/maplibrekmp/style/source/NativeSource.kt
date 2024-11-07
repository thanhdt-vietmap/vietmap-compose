package dev.sargunv.maplibrekmp.style.source

import cocoapods.MapLibre.MLNSource

internal actual class NativeSource private actual constructor(override val id: String) : Source() {
  private lateinit var _impl: MLNSource

  constructor(source: MLNSource) : this(source.identifier) {
    _impl = source
  }

  override val impl
    get() = _impl
}
