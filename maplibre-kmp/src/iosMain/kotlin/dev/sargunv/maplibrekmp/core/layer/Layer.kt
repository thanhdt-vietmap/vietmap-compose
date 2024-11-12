package dev.sargunv.maplibrekmp.core.layer

import cocoapods.MapLibre.MLNStyleLayer
import dev.sargunv.maplibrekmp.core.source.Source

internal actual sealed class Layer {
  abstract val impl: MLNStyleLayer

  actual abstract val id: String
  actual abstract val source: Source

  actual var minZoom: Float
    get() = impl.minimumZoomLevel
    set(value) {
      impl.minimumZoomLevel = value
    }

  actual var maxZoom: Float
    get() = impl.maximumZoomLevel
    set(value) {
      impl.maximumZoomLevel = value
    }

  actual var visible: Boolean
    get() = impl.visible
    set(value) {
      impl.visible = value
    }
}
