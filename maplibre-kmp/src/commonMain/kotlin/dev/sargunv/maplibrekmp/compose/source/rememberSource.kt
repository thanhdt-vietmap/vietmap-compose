package dev.sargunv.maplibrekmp.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.sargunv.maplibrekmp.compose.LocalStyleManager
import dev.sargunv.maplibrekmp.core.source.Source

@Composable
@PublishedApi
internal fun <T : Source> rememberSource(
  key: String,
  factory: (id: String) -> T,
  update: T.() -> Unit,
): SourceHandle {
  val styleManager = LocalStyleManager.current
  val source =
    remember(factory, key, styleManager) { factory(key).also { styleManager.enqueueAddSource(it) } }
  remember(source, update) { source.update() }

  DisposableEffect(styleManager, source) { onDispose { styleManager.enqueueRemoveSource(source) } }

  return remember(source) { SourceHandle(source) }
}

public data class SourceHandle internal constructor(@PublishedApi internal val source: Source)
