package dev.sargunv.maplibrecompose.demoapp.demos

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import dev.sargunv.maplibrecompose.compose.MaplibreMap
import dev.sargunv.maplibrecompose.core.data.OrnamentSettings

@Composable
fun EdgeToEdgeDemo(innerPadding: PaddingValues) {
  MaplibreMap(ornamentSettings = OrnamentSettings(padding = innerPadding))
}
