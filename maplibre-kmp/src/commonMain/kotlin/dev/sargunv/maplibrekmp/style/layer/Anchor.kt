package dev.sargunv.maplibrekmp.style.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

@Immutable
public sealed interface Anchor {
  public data object Top : Anchor

  public data object Bottom : Anchor

  public data class Above(val layerId: String) : Anchor

  public data class Below(val layerId: String) : Anchor

  public companion object {
    @Composable
    public fun Top(block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Top) { block() }
    }

    @Composable
    public fun Bottom(block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Bottom) { block() }
    }

    @Composable
    public fun Above(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Above(layerId)) { block() }
    }

    @Composable
    public fun Below(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Below(layerId)) { block() }
    }
  }
}

internal val LocalAnchor: ProvidableCompositionLocal<Anchor> = compositionLocalOf { Anchor.Top }
