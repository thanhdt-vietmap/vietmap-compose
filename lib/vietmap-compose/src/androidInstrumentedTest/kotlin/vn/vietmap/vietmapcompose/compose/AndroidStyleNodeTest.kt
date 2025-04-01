package vn.vietmap.vietmapcompose.compose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runAndroidComposeUiTest
import vn.vietmap.vietmapsdk.Vietmap

@OptIn(ExperimentalTestApi::class)
class AndroidStyleNodeTest : StyleNodeTest() {
  override fun platformSetup() =
    runAndroidComposeUiTest<ComponentActivity> {
      activity!!.runOnUiThread { Vietmap.getInstance(activity!!) }
    }
}
