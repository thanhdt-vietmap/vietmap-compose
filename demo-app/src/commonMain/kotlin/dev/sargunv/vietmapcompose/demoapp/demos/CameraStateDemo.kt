package dev.sargunv.vietmapcompose.demoapp.demos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.compose.rememberCameraState
import dev.sargunv.vietmapcompose.compose.rememberStyleState
import dev.sargunv.vietmapcompose.core.CameraPosition
import dev.sargunv.vietmapcompose.demoapp.DEFAULT_STYLE
import dev.sargunv.vietmapcompose.demoapp.Demo
import dev.sargunv.vietmapcompose.demoapp.DemoMapControls
import dev.sargunv.vietmapcompose.demoapp.DemoOrnamentSettings
import dev.sargunv.vietmapcompose.demoapp.DemoScaffold
import dev.sargunv.vietmapcompose.demoapp.format
import io.github.dellisd.spatialk.geojson.Position
import kotlin.math.roundToInt

private val CHICAGO = Position(latitude = 41.878, longitude = -87.626)

object CameraStateDemo : Demo {
  override val name = "Camera state"
  override val description = "Read camera position as state."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    DemoScaffold(this, navigateUp) {
      Column {
        val cameraState =
          rememberCameraState(firstPosition = CameraPosition(target = CHICAGO, zoom = 12.0))
        val styleState = rememberStyleState()

        Box(modifier = Modifier.weight(1f)) {
          MaplibreMap(
            styleUri = DEFAULT_STYLE,
            cameraState = cameraState,
            styleState = styleState,
            ornamentSettings = DemoOrnamentSettings(),
          )
          DemoMapControls(cameraState, styleState)
        }

        Row(modifier = Modifier.safeDrawingPadding().wrapContentSize(Alignment.Center)) {
          val pos = cameraState.position
          val scale = cameraState.metersPerDpAtTarget

          Cell("Latitude", pos.target.latitude.format(3), Modifier.weight(1.4f))
          Cell("Longitude", pos.target.longitude.format(3), Modifier.weight(1.4f))
          Cell("Zoom", pos.zoom.format(2), Modifier.weight(1f))
          Cell("Bearing", pos.bearing.format(2), Modifier.weight(1f))
          Cell("Tilt", pos.tilt.format(2), Modifier.weight(1f))
          Cell("Scale", "${scale.roundToInt()}m", Modifier.weight(1f))
        }
      }
    }
  }
}

@Composable
private fun Cell(title: String, value: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier.padding(PaddingValues(4.dp)).wrapContentSize(Alignment.Center)) {
    Text(
      text = title,
      textAlign = TextAlign.Center,
      maxLines = 1,
      style = MaterialTheme.typography.labelSmall,
    )
    Text(
      text = value,
      textAlign = TextAlign.Center,
      maxLines = 1,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}
