package dev.sargunv.maplibrekmp.internal.wrapper.source

import cocoapods.MapLibre.MLNSource
import dev.sargunv.maplibrekmp.internal.wrapper.source.Source

internal actual class NativeSource private actual constructor(override val id: String) : Source() {
  private lateinit var _impl: MLNSource

  constructor(source: MLNSource) : this(source.identifier) {
    _impl = source
  }

  override val impl
    get() = _impl
}
