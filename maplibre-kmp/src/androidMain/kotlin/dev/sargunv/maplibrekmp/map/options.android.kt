package dev.sargunv.maplibrekmp.map

import android.view.Gravity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import dev.sargunv.maplibrekmp.style.layer.Layer
import dev.sargunv.maplibrekmp.style.source.Source
import org.maplibre.android.maps.MapView

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
      options.sources
        .map { (id, source) ->
          when (source) {
            is Source.GeoJson -> source.toNativeSource(id)
          }
        }
        .forEach { style.addSource(it) }

      options.layers
        .associateBy({ it }) { layer ->
          when (layer.type) {
            is Layer.Type.Line -> layer.type.toNativeLayer(layer)
          }
        }
        .forEach { (layer, nativeLayer) ->
          when {
            layer.below != null -> style.addLayerBelow(nativeLayer, layer.below)
            layer.above != null -> style.addLayerAbove(nativeLayer, layer.above)
            layer.index != null -> style.addLayerAt(nativeLayer, layer.index)
            else -> style.addLayer(nativeLayer)
          }
        }
    }
  }
}
