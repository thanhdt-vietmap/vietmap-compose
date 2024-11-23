package dev.sargunv.maplibrecompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import dev.sargunv.maplibrecompose.core.source.Source
import dev.sargunv.maplibrecompose.core.source.VectorSource

@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun rememberVectorSource(id: String, configUrl: String): Source =
  composeKey(id, configUrl) {
    rememberUserSource(factory = { VectorSource(id = id, configUrl = configUrl) }, update = {})
  }
