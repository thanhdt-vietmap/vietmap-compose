package dev.sargunv.vietmapcompose.demoapp.demos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import dev.sargunv.vietmapcompose.compose.MaplibreMap
import dev.sargunv.vietmapcompose.compose.rememberCameraState
import dev.sargunv.vietmapcompose.compose.rememberStyleState
import dev.sargunv.vietmapcompose.core.CameraPosition
import dev.sargunv.vietmapcompose.demoapp.ALL_STYLES
import dev.sargunv.vietmapcompose.demoapp.Demo
import dev.sargunv.vietmapcompose.demoapp.DemoMapControls
import dev.sargunv.vietmapcompose.demoapp.DemoOrnamentSettings
import dev.sargunv.vietmapcompose.demoapp.DemoScaffold
import dev.sargunv.vietmapcompose.demoapp.getDefaultColorScheme
import io.github.dellisd.spatialk.geojson.Position

private val NEW_YORK = Position(latitude = 40.744, longitude = -73.981)

object StyleSwitcherDemo : Demo {
  override val name = "Style switcher"
  override val description = "Switch between different map styles at runtime."

  @Composable
  override fun Component(navigateUp: () -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }

    MaterialTheme(colorScheme = getDefaultColorScheme(isDark = ALL_STYLES[selectedIndex].isDark)) {
      DemoScaffold(this, navigateUp) {
        Column {
          val cameraState =
            rememberCameraState(CameraPosition(target = NEW_YORK, zoom = 15.0, tilt = 30.0))
          val styleState = rememberStyleState()

          Box(modifier = Modifier.weight(1f)) {
            MaplibreMap(
              styleUri = ALL_STYLES[selectedIndex].uri,
              cameraState = cameraState,
              styleState = styleState,
              ornamentSettings = DemoOrnamentSettings(),
            )
            DemoMapControls(cameraState, styleState)
          }

          SecondaryScrollableTabRow(selectedTabIndex = selectedIndex) {
            ALL_STYLES.forEachIndexed { index, style ->
              Tab(
                selected = selectedIndex == index,
                onClick = { selectedIndex = index },
                text = { Text(text = style.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
              )
            }
          }
        }
      }
    }
  }
}
