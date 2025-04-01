package vn.vietmap.vietmapcompose.core.util

import androidx.compose.runtime.Composable
import vn.vietmap.vietmapcompose.compose.LocalVietMapContext

public actual object PlatformUtils {
  @Composable
  public actual fun getSystemRefreshRate(): Float =
    LocalVietMapContext.current.refreshRate.toFloat()
}
