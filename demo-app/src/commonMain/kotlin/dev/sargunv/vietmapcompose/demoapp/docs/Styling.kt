@file:Suppress("unused")

package dev.sargunv.vietmapcompose.demoapp.docs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.demoapp.generated.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Styling() {
  // -8<- [start:simple]
  MaplibreMap(styleUri = "https://tiles.openfreemap.org/styles/liberty")
  // -8<- [end:simple]

  // -8<- [start:dynamic]
  val variant = if (isSystemInDarkTheme()) "dark" else "light"
  MaplibreMap(styleUri = "https://api.protomaps.com/styles/v4/$variant/en.json?key=MY_KEY")
  // -8<- [end:dynamic]

  // -8<- [start:local]
  MaplibreMap(styleUri = Res.getUri("files/style.json"))
  // -8<- [end:local]
}
