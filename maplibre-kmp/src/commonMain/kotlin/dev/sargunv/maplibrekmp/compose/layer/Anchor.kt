package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

internal val LocalAnchor: ProvidableCompositionLocal<Anchor> = compositionLocalOf { Anchor.Top }

@Immutable
public sealed interface Anchor {
  public data object Top : Anchor

  public data object Bottom : Anchor

  public data class Above(override val layerId: String) : WithLayerId

  public data class Below(override val layerId: String) : WithLayerId

  public data class Replace(override val layerId: String) : WithLayerId

  public sealed interface WithLayerId : Anchor {
    public val layerId: String
  }

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

    @Composable
    public fun Replace(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Replace(layerId)) { block() }
    }

    @Composable
    public fun Of(anchor: Anchor, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides anchor) { block() }
    }
  }
}
