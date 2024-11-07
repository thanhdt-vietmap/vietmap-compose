package dev.sargunv.maplibrekmp.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sargunv.maplibrekmp.compose.StyleScope

public data class MaplibreMapOptions(
  val style: StyleOptions = StyleOptions(),
  val ui: UiOptions = UiOptions(),
) {
  public data class StyleOptions(val url: String = "https://demotiles.maplibre.org/style.json")

  public data class UiOptions(
    val padding: PaddingValues = PaddingValues(8.dp),
    val isLogoEnabled: Boolean = true,
    val isAttributionEnabled: Boolean = true,
    val isCompassEnabled: Boolean = true,
    val isTiltGesturesEnabled: Boolean = true,
    val isZoomGesturesEnabled: Boolean = true,
    val isRotateGesturesEnabled: Boolean = true,
    val isScrollGesturesEnabled: Boolean = true,
  )
}

@Composable
public expect fun MaplibreMap(
  modifier: Modifier = Modifier,
  options: MaplibreMapOptions = MaplibreMapOptions(),
  styleContent: @Composable StyleScope.() -> Unit = {},
)
