package dev.sargunv.maplibrekmp.internal.wrapper.layer

import dev.sargunv.maplibrekmp.internal.wrapper.source.Source
import org.maplibre.android.style.layers.Layer
import org.maplibre.android.style.layers.Property
import org.maplibre.android.style.layers.PropertyFactory

internal actual sealed class Layer {
  abstract val impl: Layer

  actual abstract val id: String
  actual abstract val source: Source

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
