package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import dev.sargunv.maplibrekmp.core.IosMap
import dev.sargunv.maplibrekmp.core.MaplibreMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import dev.sargunv.maplibrekmp.core.toCGSize
import io.github.dellisd.spatialk.geojson.Position

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  update: (map: MaplibreMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: MaplibreMap, style: Style) -> Unit,
  onCameraMove: (map: MaplibreMap) -> Unit,
  onClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: MaplibreMap, latLng: Position, xy: XY) -> Unit,
) {
  MeasuredBox(modifier = modifier.fillMaxSize()) { measuredSize ->
    val layoutDir = LocalLayoutDirection.current
    val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

    val currentOnReset by rememberUpdatedState(onReset)
    var currentMap by remember { mutableStateOf<IosMap?>(null) }

    UIKitView(
      modifier = modifier.fillMaxSize(),
      properties =
        UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
      factory = {
        MLNMapView().also { mapView ->
          IosMap(
              mapView = mapView,
              size = measuredSize.toCGSize(),
              layoutDir = layoutDir,
              insetPadding = insetPadding,
              onStyleChanged = onStyleChanged,
              onCameraMove = onCameraMove,
              onClick = onClick,
              onLongClick = onLongClick,
              onMapLoaded = { currentMap = it },
            )
            .also { it.styleUrl = styleUrl }
        }
      },
      update = { _ ->
        val map = currentMap ?: return@UIKitView
        map.size = measuredSize.toCGSize()
        map.layoutDir = layoutDir
        map.insetPadding = insetPadding
        map.onStyleChanged = onStyleChanged
        map.onCameraMove = onCameraMove
        map.onClick = onClick
        map.onLongClick = onLongClick
        map.styleUrl = styleUrl
        update(map)
      },
      onReset = {
        currentOnReset()
        currentMap = null
      },
    )
  }
}
