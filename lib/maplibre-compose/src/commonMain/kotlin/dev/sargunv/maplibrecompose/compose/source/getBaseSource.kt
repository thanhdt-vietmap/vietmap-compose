package dev.sargunv.maplibrecompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.sargunv.maplibrecompose.compose.engine.LocalStyleManager
import dev.sargunv.maplibrecompose.core.source.Source

@Composable
public fun getBaseSource(id: String): Source {
  val styleManager = LocalStyleManager.current
  return remember(styleManager, id) { styleManager.getBaseSource(id) }
}
