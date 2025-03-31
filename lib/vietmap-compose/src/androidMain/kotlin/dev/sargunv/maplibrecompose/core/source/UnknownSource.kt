package dev.sargunv.maplibrecompose.core.source

import vn.vietmap.vietmapsdk.style.sources.Source as MLNSource

public actual class UnknownSource(override val impl: MLNSource) : Source()
