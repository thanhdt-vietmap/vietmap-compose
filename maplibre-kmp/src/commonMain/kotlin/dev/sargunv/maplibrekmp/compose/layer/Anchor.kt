package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.sargunv.maplibrekmp.core.layer.Anchor

public sealed interface Anchor {
  public companion object {
    @Composable
    public fun Top(block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Anchor.Top) { block() }
    }

    @Composable
    public fun Bottom(block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Anchor.Bottom) { block() }
    }

    @Composable
    public fun Above(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Anchor.Above(layerId)) { block() }
    }

    @Composable
    public fun Below(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Anchor.Below(layerId)) { block() }
    }

    @Composable
    public fun Replace(layerId: String, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides Anchor.Replace(layerId)) { block() }
    }
  }
}

internal val LocalAnchor: ProvidableCompositionLocal<Anchor> = compositionLocalOf { Anchor.Top }
