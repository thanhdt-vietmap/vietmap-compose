package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.core.source.VectorSource
import androidx.compose.runtime.key as composeKey

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberVectorSource(id: String, configUrl: String): Source =
  composeKey(id, configUrl) {
    rememberUserSource(factory = { VectorSource(id = id, configUrl = configUrl) }, update = {})
  }
