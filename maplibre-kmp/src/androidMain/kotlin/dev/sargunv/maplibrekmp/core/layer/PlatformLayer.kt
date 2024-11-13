package dev.sargunv.maplibrekmp.core.layer

import org.maplibre.android.style.layers.Layer as MLNLayer

@PublishedApi internal actual class PlatformLayer(override val impl: MLNLayer) : Layer()
