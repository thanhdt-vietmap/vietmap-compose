@file:Suppress("unused")

package vn.vietmap.vietmapcompose.demoapp.docs

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.demoapp.generated.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
@OptIn(ExperimentalResourceApi::class)
fun Styling() {
  // -8<- [start:simple]
  VietMapGLCompose(styleUri = "https://tiles.openfreemap.org/styles/liberty")
  // -8<- [end:simple]

  // -8<- [start:dynamic]
  val variant = if (isSystemInDarkTheme()) "dark" else "light"
  VietMapGLCompose(styleUri = "https://api.protomaps.com/styles/v4/$variant/en.json?key=MY_KEY")
  // -8<- [end:dynamic]

  // -8<- [start:local]
  VietMapGLCompose(styleUri = Res.getUri("files/style.json"))
  // -8<- [end:local]
}
