package dev.sargunv.maplibrekmp.core.source

import cocoapods.MapLibre.MLNSource

@PublishedApi internal actual class PlatformSource(override val impl: MLNSource) : Source()
