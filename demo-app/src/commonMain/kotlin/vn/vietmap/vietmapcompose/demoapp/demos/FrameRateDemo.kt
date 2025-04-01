package vn.vietmap.vietmapcompose.demoapp.demos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.compose.rememberCameraState
import vn.vietmap.vietmapcompose.compose.rememberStyleState
import vn.vietmap.vietmapcompose.core.util.PlatformUtils
import vn.vietmap.vietmapcompose.demoapp.DEFAULT_STYLE
import vn.vietmap.vietmapcompose.demoapp.Demo
import vn.vietmap.vietmapcompose.demoapp.DemoMapControls
import vn.vietmap.vietmapcompose.demoapp.DemoOrnamentSettings
import vn.vietmap.vietmapcompose.demoapp.DemoScaffold
import vn.vietmap.vietmapcompose.demoapp.FrameRateState
import vn.vietmap.vietmapcompose.demoapp.Platform
import vn.vietmap.vietmapcompose.demoapp.usesVietMapNative
import kotlin.math.roundToInt

object FrameRateDemo : Demo {
  override val name = "Frame rate"
  override val description = "Change the frame rate of the map."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    DemoScaffold(this, navigateUp) {
      Column {
        val systemRefreshRate = PlatformUtils.getSystemRefreshRate().roundToInt()
        var maximumFps by remember { mutableStateOf(systemRefreshRate) }
        val fpsState = remember { FrameRateState() }

        val cameraState = rememberCameraState()
        val styleState = rememberStyleState()

        Box(modifier = Modifier.weight(1f)) {
          VietMapGLCompose(
            styleUri = DEFAULT_STYLE,
            maximumFps = maximumFps,
            onFrame = fpsState::recordFps,
            cameraState = cameraState,
            styleState = styleState,
            ornamentSettings = DemoOrnamentSettings(),
          )
          DemoMapControls(cameraState, styleState)
        }

        Column(modifier = Modifier.padding(16.dp)) {
          Slider(
            value = maximumFps.toFloat(),
            onValueChange = { maximumFps = it.roundToInt() },
            valueRange = 15f..systemRefreshRate.toFloat().coerceAtLeast(15f),
            enabled = Platform.usesVietMapNative,
          )
          Text(
            "Target: $maximumFps ${fpsState.spinChar} Actual: ${fpsState.avgFps}",
            style = MaterialTheme.typography.labelMedium,
          )
        }
      }
    }
  }
}
