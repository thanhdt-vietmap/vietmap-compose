package dev.sargunv.maplibrecompose.compose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runAndroidComposeUiTest
import org.maplibre.android.MapLibre

@OptIn(ExperimentalTestApi::class)
class AndroidStyleManagerTest : StyleManagerTest() {
  override fun platformSetup() =
    runAndroidComposeUiTest<ComponentActivity> {
      activity!!.runOnUiThread { MapLibre.getInstance(activity!!) }
    }
}
