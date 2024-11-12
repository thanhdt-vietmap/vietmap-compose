package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface Anchor {
  data object Top : Anchor

  data object Bottom : Anchor

  data class Above(val layerId: String) : Anchor

  data class Below(val layerId: String) : Anchor
}
