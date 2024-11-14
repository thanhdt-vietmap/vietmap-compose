package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface Anchor {
  fun validate(baseLayers: Map<String, PlatformLayer>): Unit = Unit

  data object Top : Anchor

  data object Bottom : Anchor

  data class Above(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, PlatformLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  data class Below(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, PlatformLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  data class Replace(val layerId: String) : Anchor {
    override fun validate(baseLayers: Map<String, PlatformLayer>) =
      requireIdInBaseLayers(baseLayers, layerId)
  }

  companion object {
    private fun requireIdInBaseLayers(baseLayers: Map<String, PlatformLayer>, layerId: String) =
      require(baseLayers.containsKey(layerId)) { "Layer ID '$layerId' not found in base style" }
  }
}
