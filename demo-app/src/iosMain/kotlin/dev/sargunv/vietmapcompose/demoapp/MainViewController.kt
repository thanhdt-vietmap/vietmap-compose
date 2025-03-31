package dev.sargunv.vietmapcompose.demoapp

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController

@Suppress("unused", "FunctionName") // called in Swift
fun MainViewController() = ComposeUIViewController { DemoApp() }

@Composable
actual fun getDefaultColorScheme(isDark: Boolean): ColorScheme {
  return if (isDark) darkColorScheme() else lightColorScheme()
}

actual object Platform {
  actual val isAndroid: Boolean = false
  actual val isIos: Boolean = true
  actual val isDesktop: Boolean = false
  actual val isWeb: Boolean = false
}
