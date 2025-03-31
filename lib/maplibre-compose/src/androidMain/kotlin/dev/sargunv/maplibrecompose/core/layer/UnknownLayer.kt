package dev.sargunv.maplibrecompose.core.layer

import vn.vietmap.vietmapsdk.style.layers.Layer as MLNLayer

internal actual class UnknownLayer(override val impl: MLNLayer) : Layer()
