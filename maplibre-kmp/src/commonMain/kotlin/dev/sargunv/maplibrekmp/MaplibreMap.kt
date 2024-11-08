package dev.sargunv.maplibrekmp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.internal.compose.MapNodeApplier
import dev.sargunv.maplibrekmp.internal.compose.StyleNode
import dev.sargunv.maplibrekmp.internal.wrapper.NativeMap
import dev.sargunv.maplibrekmp.internal.wrapper.Style
import dev.sargunv.maplibrekmp.style.StyleScope
import kotlinx.coroutines.awaitCancellation

@Composable
public fun MaplibreMap(
  modifier: Modifier = Modifier,
  styleUrl: String = "https://demotiles.maplibre.org/style.json",
  uiPadding: PaddingValues = PaddingValues(8.dp),
  isDebugEnabled: Boolean = false,
  isLogoEnabled: Boolean = true,
  isAttributionEnabled: Boolean = true,
  isCompassEnabled: Boolean = true,
  isTiltGesturesEnabled: Boolean = true,
  isZoomGesturesEnabled: Boolean = true,
  isRotateGesturesEnabled: Boolean = true,
  isScrollGesturesEnabled: Boolean = true,
  styleContent: @Composable StyleScope.() -> Unit = {},
) {
  var style by remember { mutableStateOf<Style?>(null) }
  val compositionContext = rememberCompositionContext()

  NativeMap(
    modifier = modifier,
    uiPadding = uiPadding,
    styleUrl = styleUrl,
    updateMap = { map ->
      map.isDebugEnabled = isDebugEnabled
      map.isLogoEnabled = isLogoEnabled
      map.isAttributionEnabled = isAttributionEnabled
      map.isCompassEnabled = isCompassEnabled
      map.isTiltGesturesEnabled = isTiltGesturesEnabled
      map.isZoomGesturesEnabled = isZoomGesturesEnabled
      map.isRotateGesturesEnabled = isRotateGesturesEnabled
      map.isScrollGesturesEnabled = isScrollGesturesEnabled
    },
    onStyleLoaded = { style = it },
    onRelease = { style = null },
  )

  LaunchedEffect(style) {
    val applier = style?.let { MapNodeApplier(StyleNode(it)) } ?: return@LaunchedEffect
    val composition = Composition(applier, compositionContext)
    composition.setContent { StyleScope.styleContent() }
    try {
      awaitCancellation()
    } finally {
      composition.dispose()
    }
  }
}
