package dev.sargunv.maplibrecompose.core.source

import cocoapods.VietMap.MLNAttributionInfo
import cocoapods.VietMap.MLNSource
import cocoapods.VietMap.MLNTileSource

public actual sealed class Source {
  internal abstract val impl: MLNSource
  internal actual val id: String by lazy { impl.identifier }

  public actual val attributionLinks: List<AttributionLink>
    get() =
      (impl as? MLNTileSource)?.attributionInfos?.mapNotNull {
        it as MLNAttributionInfo
        if (it.URL == null) return@mapNotNull null
        AttributionLink(title = it.title.string(), url = it.URL.toString())
      } ?: emptyList()

  override fun toString(): String = "${this::class.simpleName}(id=\"$id\")"
}
