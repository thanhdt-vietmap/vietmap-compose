package dev.sargunv.maplibrekmp.core.layer

import dev.sargunv.maplibrekmp.core.source.Source

@PublishedApi
internal actual sealed class UserLayer actual constructor(actual val source: Source) : Layer() {

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
