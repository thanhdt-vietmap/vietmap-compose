package dev.sargunv.vietmapcompose.demoapp

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.singleWindowApplication
import dev.sargunv.vietmapcompose.compose.KcefProvider
import dev.sargunv.vietmapcompose.compose.MaplibreContextProvider

// TODO This should enable support for blending Compose over Swing views
// but it's just crashing on launch
// https://github.com/JetBrains/compose-multiplatform-core/pull/915#issuecomment-1954155894
// System.setProperty("compose.interop.blending", "true")

// -8<- [start:main]
fun main() {
  singleWindowApplication {
    KcefProvider(
      loading = { Text("Performing first time setup ...") },
      content = { MaplibreContextProvider { DemoApp() } },
    )
  }
}

// -8<- [end:main]

@Composable
actual fun getDefaultColorScheme(isDark: Boolean): ColorScheme {
  return if (isDark) darkColorScheme() else lightColorScheme()
}

actual object Platform {
  actual val isAndroid: Boolean = false
  actual val isIos: Boolean = false
  actual val isDesktop: Boolean = true
  actual val isWeb: Boolean = false
}
