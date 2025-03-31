package dev.sargunv.vietmapcompose.core.util

import androidx.compose.runtime.Composable
import dev.sargunv.vietmapcompose.compose.LocalMaplibreContext

public actual object PlatformUtils {
  @Composable
  public actual fun getSystemRefreshRate(): Float =
    LocalMaplibreContext.current.refreshRate.toFloat()
}
