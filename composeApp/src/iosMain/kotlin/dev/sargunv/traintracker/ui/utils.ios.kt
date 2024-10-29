package dev.sargunv.traintracker.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@Composable
actual fun getColorScheme() = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getSheetHeight(): Dp {
  val fullWindowHeight = LocalWindowInfo.current.containerSize.height
  val topSafeInset = WindowInsets.safeDrawing.getTop(LocalDensity.current)
  return with(LocalDensity.current) { (fullWindowHeight - topSafeInset).toDp() }
}
