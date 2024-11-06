package dev.sargunv.maplibrekmp.map

import android.view.Gravity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import dev.sargunv.maplibrekmp.style.Layer
import dev.sargunv.maplibrekmp.style.Source
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.Style

internal fun MapView.applyUiOptions(
  options: MaplibreMapOptions.UiOptions,
  density: Density,
  layoutDir: LayoutDirection,
) {
  getMapAsync { map ->
    map.uiSettings.attributionGravity = Gravity.BOTTOM or Gravity.END

    map.isDebugActive = true

    map.uiSettings.isLogoEnabled = options.isLogoEnabled
    map.uiSettings.isAttributionEnabled = options.isAttributionEnabled
    map.uiSettings.isCompassEnabled = options.isCompassEnabled

    map.uiSettings.isTiltGesturesEnabled = options.isTiltGesturesEnabled
    map.uiSettings.isZoomGesturesEnabled = options.isZoomGesturesEnabled
    map.uiSettings.isRotateGesturesEnabled = options.isRotateGesturesEnabled
    map.uiSettings.isScrollGesturesEnabled = options.isScrollGesturesEnabled

    with(density) {
      val topUiPadding = options.padding.calculateTopPadding().roundToPx()
      val bottomUiPadding = options.padding.calculateBottomPadding().roundToPx()
      val leftUiPadding = options.padding.calculateLeftPadding(layoutDir).roundToPx()
      val rightUiPadding = options.padding.calculateRightPadding(layoutDir).roundToPx()

      map.uiSettings.setAttributionMargins(
        leftUiPadding,
        topUiPadding,
        rightUiPadding,
        bottomUiPadding,
      )
      map.uiSettings.setLogoMargins(leftUiPadding, topUiPadding, rightUiPadding, bottomUiPadding)
      map.uiSettings.setCompassMargins(leftUiPadding, topUiPadding, rightUiPadding, bottomUiPadding)
    }
  }
}

internal fun MapView.applyStyleOptions(options: MaplibreMapOptions.StyleOptions) {
  getMapAsync { map ->
    map.setStyle(options.url.correctedAndroidUri().toString()) { style ->
      options.block(StyleScope.Default(NativeStyleManager(style)))
    }
  }
}

internal class NativeStyleManager(private val style: Style) : StyleManager() {
  override fun registerSource(source: Source) = style.addSource(source.toNativeSource())

  override fun registerLayer(layer: Layer) = style.addLayer(layer.toNativeLayer())

  override fun registerLayerAbove(id: String, layer: Layer) =
    style.addLayerAbove(layer.toNativeLayer(), id)

  override fun registerLayerBelow(id: String, layer: Layer) =
    style.addLayerBelow(layer.toNativeLayer(), id)

  override fun registerLayerAt(index: Int, layer: Layer) =
    style.addLayerAt(layer.toNativeLayer(), index)

  override fun hasSource(id: String) = style.getSource(id) != null

  override fun hasLayer(id: String) = style.getLayer(id) != null
}
