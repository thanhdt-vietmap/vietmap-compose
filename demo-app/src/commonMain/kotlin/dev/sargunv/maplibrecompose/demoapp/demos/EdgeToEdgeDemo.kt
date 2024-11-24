package dev.sargunv.maplibrecompose.demoapp.demos

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import dev.sargunv.maplibrecompose.compose.MaplibreMap
import dev.sargunv.maplibrecompose.core.data.OrnamentSettings
import dev.sargunv.maplibrecompose.demoapp.DEFAULT_STYLE

@Composable
fun EdgeToEdgeDemo(innerPadding: PaddingValues) {
  MaplibreMap(styleUrl = DEFAULT_STYLE, ornamentSettings = OrnamentSettings(padding = innerPadding))
}
