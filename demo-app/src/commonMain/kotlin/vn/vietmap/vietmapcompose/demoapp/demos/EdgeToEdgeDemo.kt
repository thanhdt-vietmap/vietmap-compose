package vn.vietmap.vietmapcompose.demoapp.demos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vn.vietmap.vietmapcompose.compose.VietMapGLCompose
import vn.vietmap.vietmapcompose.compose.rememberCameraState
import vn.vietmap.vietmapcompose.compose.rememberStyleState
import vn.vietmap.vietmapcompose.core.CameraPosition
import vn.vietmap.vietmapcompose.demoapp.DEFAULT_STYLE
import vn.vietmap.vietmapcompose.demoapp.Demo
import vn.vietmap.vietmapcompose.demoapp.DemoAppBar
import vn.vietmap.vietmapcompose.demoapp.DemoMapControls
import vn.vietmap.vietmapcompose.demoapp.DemoOrnamentSettings
import io.github.dellisd.spatialk.geojson.Position

private val PORTLAND = Position(latitude = 45.521, longitude = -122.675)

object EdgeToEdgeDemo : Demo {
  override val name = "Edge-to-edge"
  override val description =
    "Fill the entire screen with a map and pad ornaments to position them correctly."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    val cameraState = rememberCameraState(CameraPosition(target = PORTLAND, zoom = 13.0))
    val styleState = rememberStyleState()

    Scaffold(topBar = { DemoAppBar(this, navigateUp, alpha = 0.5f) }) { padding ->
      Box(modifier = Modifier.consumeWindowInsets(WindowInsets.safeContent).fillMaxSize()) {
        VietMapGLCompose(
          styleUri = DEFAULT_STYLE,
          cameraState = cameraState,
          styleState = styleState,
          ornamentSettings = DemoOrnamentSettings(padding),
        )
        DemoMapControls(cameraState, styleState, modifier = Modifier.padding(padding))
      }
    }
  }
}
