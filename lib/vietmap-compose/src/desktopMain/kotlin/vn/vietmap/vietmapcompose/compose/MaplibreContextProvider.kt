package vn.vietmap.vietmapcompose.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.FrameWindowScope
import vn.vietmap.vietmapcompose.generated.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
@OptIn(ExperimentalResourceApi::class)
public fun FrameWindowScope.VietMapContextProvider(content: @Composable () -> Unit) {
  val refreshRate = window.graphicsConfiguration.device.displayMode.refreshRate
  var webviewHtml by remember { mutableStateOf<String?>(null) }

  LaunchedEffect(webviewHtml) {
    if (webviewHtml != null) return@LaunchedEffect
    webviewHtml = Res.readBytes("files/maplibre-compose-webview.html").toString(Charsets.UTF_8)
  }

  if (webviewHtml == null) return

  val context = remember(refreshRate, webviewHtml) { VietMapContext(refreshRate, webviewHtml!!) }

  CompositionLocalProvider(LocalVietMapContext provides context) { content() }
}

internal data class VietMapContext(val refreshRate: Int, val webviewHtml: String)

internal val LocalVietMapContext =
  compositionLocalOf<VietMapContext> {
    error("No VietMapContext provided; wrap your app with VietMapContextProvider")
  }
