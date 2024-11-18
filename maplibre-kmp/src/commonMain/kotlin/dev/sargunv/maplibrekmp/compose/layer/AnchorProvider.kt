package dev.sargunv.maplibrekmp.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import dev.sargunv.maplibrekmp.core.layer.UnspecifiedLayer

public sealed interface AnchorProvider {
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

    @Composable
    internal fun Of(anchor: Anchor, block: @Composable () -> Unit) {
      CompositionLocalProvider(LocalAnchor provides anchor) { block() }
    }
  }
}

internal val LocalAnchor: ProvidableCompositionLocal<Anchor> = compositionLocalOf { Anchor.Top }

@Immutable
internal sealed interface Anchor {
  fun validate(baseLayers: Map<String, UnspecifiedLayer>): Unit = Unit

  data object Top : Anchor

  data object Bottom : Anchor

  data class Above(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, UnspecifiedLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  data class Below(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, UnspecifiedLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  data class Replace(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, UnspecifiedLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  companion object {
    private fun requireIdInBaseLayers(baseLayers: Map<String, UnspecifiedLayer>, layerId: String) =
      require(baseLayers.containsKey(layerId)) { "Layer ID '$layerId' not found in base style" }
  }
}
