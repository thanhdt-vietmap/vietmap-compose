package dev.sargunv.maplibrekmp.core.source

import org.maplibre.android.style.sources.Source as MLNSource

@PublishedApi internal actual class PlatformSource(override val impl: MLNSource) : Source()
