package dev.sargunv.maplibrekmp.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.compose.engine.MapNodeApplier
import dev.sargunv.maplibrekmp.compose.engine.StyleNode
import dev.sargunv.maplibrekmp.core.Style
import dev.sargunv.maplibrekmp.core.StyleManager
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.ExpressionScope
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
  styleContent: @Composable ExpressionScope.() -> Unit = {},
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
    style?.let { style ->
      val rootNode = StyleNode(style)
      val composition = Composition(MapNodeApplier(rootNode), compositionContext)
      composition.setContent {
        CompositionLocalProvider(LocalStyleManager provides rootNode.styleManager) {
          styleContent(Expression.Companion)
        }
      }
      try {
        awaitCancellation()
      } finally {
        composition.dispose()
      }
    }
  }
}

internal val LocalStyleManager =
  staticCompositionLocalOf<StyleManager> { throw IllegalStateException() }
