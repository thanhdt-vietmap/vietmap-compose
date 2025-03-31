@file:Suppress("unused")

package dev.sargunv.vietmapcompose.demoapp.docs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.compose.rememberCameraState
import dev.sargunv.vietmapcompose.compose.rememberStyleState
import dev.sargunv.vietmapcompose.core.OrnamentSettings
import dev.sargunv.vietmapcompose.material3.controls.AttributionButton
import dev.sargunv.vietmapcompose.material3.controls.CompassButton
import dev.sargunv.vietmapcompose.material3.controls.DisappearingCompassButton
import dev.sargunv.vietmapcompose.material3.controls.DisappearingScaleBar
import dev.sargunv.vietmapcompose.material3.controls.ScaleBar

@Composable
fun Material3() {
  // -8<- [start:controls]
  val cameraState = rememberCameraState()
  val styleState = rememberStyleState()

  Box(Modifier.fillMaxSize()) {
    MaplibreMap(
      cameraState = cameraState,
      styleState = styleState,
      ornamentSettings = OrnamentSettings.AllDisabled,
    )

    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
      ScaleBar(cameraState.metersPerDpAtTarget, modifier = Modifier.align(Alignment.TopStart))
      CompassButton(cameraState, modifier = Modifier.align(Alignment.TopEnd))
      AttributionButton(styleState, modifier = Modifier.align(Alignment.BottomEnd))
    }
  }
  // -8<- [end:controls]

  // -8<- [start:disappearing-controls]
  Box(modifier = Modifier.fillMaxSize()) {
    MaplibreMap(
      cameraState = cameraState,
      styleState = styleState,
      ornamentSettings = OrnamentSettings.AllDisabled,
    )

    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
      DisappearingScaleBar(
        metersPerDp = cameraState.metersPerDpAtTarget,
        zoom = cameraState.position.zoom,
        modifier = Modifier.align(Alignment.TopStart),
      ) // (1)!
      DisappearingCompassButton(cameraState, modifier = Modifier.align(Alignment.TopEnd)) // (2)!
      AttributionButton(styleState, modifier = Modifier.align(Alignment.BottomEnd))
    }
  }
  // -8<- [end:disappearing-controls]
}
