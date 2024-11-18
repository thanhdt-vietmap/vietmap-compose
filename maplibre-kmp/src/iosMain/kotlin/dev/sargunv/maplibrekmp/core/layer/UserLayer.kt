package dev.sargunv.maplibrekmp.core.layer

@PublishedApi
internal actual sealed class UserLayer actual constructor() : Layer() {

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
