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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.MapLibre.MLNMapView
import dev.sargunv.maplibrekmp.core.PlatformMap
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.data.XY
import dev.sargunv.maplibrekmp.core.toCGSize
import io.github.dellisd.spatialk.geojson.Position

@Composable
internal actual fun PlatformMapView(
  modifier: Modifier,
  styleUrl: String,
  update: (map: PlatformMap) -> Unit,
  onReset: () -> Unit,
  onStyleChanged: (map: PlatformMap, style: Style) -> Unit,
  onCameraMove: (map: PlatformMap) -> Unit,
  onClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
  onLongClick: (map: PlatformMap, latLng: Position, xy: XY) -> Unit,
) {
  val layoutDir = LocalLayoutDirection.current
  val density = LocalDensity.current
  val insetPadding = WindowInsets.safeDrawing.asPaddingValues()

  val currentOnReset by rememberUpdatedState(onReset)

  var size by remember { mutableStateOf(DpSize.Unspecified) }
  var currentMap by remember { mutableStateOf<PlatformMap?>(null) }

  UIKitView(
    modifier =
      modifier.fillMaxSize().onSizeChanged { size = with(density) { it.toSize().toDpSize() } },
    properties =
      UIKitInteropProperties(interactionMode = UIKitInteropInteractionMode.NonCooperative),
    factory = ::MLNMapView,
    update = { mapView ->
      val map =
        currentMap
          ?: if (size.isSpecified)
            PlatformMap(mapView, size.toCGSize(), layoutDir, insetPadding).also { currentMap = it }
          else return@UIKitView

      if (size.isSpecified) map.size = size.toCGSize()
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
