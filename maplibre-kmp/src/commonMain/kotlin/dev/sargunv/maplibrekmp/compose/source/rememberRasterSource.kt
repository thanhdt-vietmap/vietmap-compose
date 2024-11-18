package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.source.RasterSource
import dev.sargunv.maplibrekmp.core.source.Source
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberRasterSource(id: String, configUrl: String): Source =
  composeKey(id, configUrl) {
    rememberUserSource(factory = { RasterSource(id = id, configUrl = configUrl) }, update = {})
  }
