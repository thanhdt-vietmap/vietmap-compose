@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("unused")

package vn.vietmap.vietmapcompose.webview

import vn.vietmap.maplibrejs.AttributionControl
import vn.vietmap.maplibrejs.LogoControl
import vn.vietmap.maplibrejs.Map
import vn.vietmap.maplibrejs.MapOptions
import vn.vietmap.maplibrejs.NavigationControl
import vn.vietmap.maplibrejs.NavigationControlOptions
import vn.vietmap.maplibrejs.ScaleControl
import kotlinx.browser.document
import org.w3c.dom.HTMLDivElement

@JsExport
object WebviewMapBridge {
  private var container: HTMLDivElement? = null
  private lateinit var map: Map
  private lateinit var navigationControl: NavigationControl
  private lateinit var logoControl: LogoControl
  private lateinit var scaleControl: ScaleControl
  private lateinit var attributionControl: AttributionControl

  fun init() {
    container =
      document.createElement("div").also {
        it.setAttribute("style", "width: 100%; height: 100vh;")
        document.body!!.appendChild(it)
      } as HTMLDivElement
    map = Map(MapOptions(container = container!!, disableAttributionControl = true))
    navigationControl = NavigationControl(NavigationControlOptions(visualizePitch = true))
    logoControl = LogoControl()
    scaleControl = ScaleControl()
    attributionControl = AttributionControl()
  }

  fun setStyleUri(styleUri: String) {
    map.setStyle(styleUri)
  }

  fun setDebugEnabled(enabled: Boolean) {
    map.showCollisionBoxes = enabled
    map.showPadding = enabled
    map.showTileBoundaries = enabled
  }

  fun setMaxZoom(maxZoom: Double) {
    map.setMaxZoom(maxZoom)
  }

  fun setMinZoom(minZoom: Double) {
    map.setMinZoom(minZoom)
  }

  fun setMaxPitch(maxPitch: Double) {
    map.setMaxPitch(maxPitch)
  }

  fun setMinPitch(minPitch: Double) {
    map.setMinPitch(minPitch)
  }

  fun addNavigationControl(position: String) {
    map.addControl(navigationControl, position)
  }

  fun removeNavigationControl() {
    map.removeControl(navigationControl)
  }

  fun addLogoControl(position: String) {
    map.addControl(logoControl, position)
  }

  fun removeLogoControl() {
    map.removeControl(logoControl)
  }

  fun addScaleControl(position: String) {
    map.addControl(scaleControl, position)
  }

  fun removeScaleControl() {
    map.removeControl(scaleControl)
  }

  fun addAttributionControl(position: String) {
    map.addControl(attributionControl, position)
  }

  fun removeAttributionControl() {
    map.removeControl(attributionControl)
  }

  fun setTiltGesturesEnabled(enabled: Boolean) {
    if (enabled) map.touchPitch.enable() else map.touchPitch.disable()
  }

  fun setZoomGesturesEnabled(enabled: Boolean) {
    if (enabled) {
      map.doubleClickZoom.enable()
      map.scrollZoom.enable()
      map.touchZoomRotate.enable()
    } else {
      map.doubleClickZoom.disable()
      map.scrollZoom.disable()
      map.touchZoomRotate.disable()
    }
  }

  fun setRotateGesturesEnabled(enabled: Boolean) {
    if (enabled) {
      map.dragRotate.enable()
      map.keyboard.enableRotation()
      map.touchZoomRotate.enableRotation()
    } else {
      map.dragRotate.disable()
      map.keyboard.disableRotation()
      map.touchZoomRotate.disableRotation()
    }
  }

  fun setScrollGesturesEnabled(enabled: Boolean) {
    if (enabled) map.dragPan.enable() else map.dragPan.disable()
  }

  fun setKeyboardGesturesEnabled(enabled: Boolean) {
    if (enabled) map.keyboard.enable() else map.keyboard.disable()
  }
}
