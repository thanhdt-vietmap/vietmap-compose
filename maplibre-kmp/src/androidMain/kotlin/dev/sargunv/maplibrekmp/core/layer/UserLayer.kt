package dev.sargunv.maplibrekmp.core.layer

import org.maplibre.android.style.layers.Property
import org.maplibre.android.style.layers.PropertyFactory

@PublishedApi
internal actual sealed class UserLayer actual constructor(actual val anchor: Anchor) : Layer() {

  actual var minZoom: Float
    get() = impl.minZoom
    set(value) {
      impl.minZoom = value
    }

  actual var maxZoom: Float
    get() = impl.maxZoom
    set(value) {
      impl.maxZoom = value
    }

  actual var visible: Boolean
    get() = impl.visibility.value == Property.VISIBLE
    set(value) {
      impl.setProperties(PropertyFactory.visibility(if (value) Property.VISIBLE else Property.NONE))
    }
}
