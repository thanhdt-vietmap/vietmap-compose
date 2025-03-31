package vn.vietmap.vietmapcompose.core.util

import androidx.compose.runtime.Composable
import vn.vietmap.vietmapcompose.compose.LocalMaplibreContext

public actual object PlatformUtils {
  @Composable
  public actual fun getSystemRefreshRate(): Float =
    LocalMaplibreContext.current.refreshRate.toFloat()
}
