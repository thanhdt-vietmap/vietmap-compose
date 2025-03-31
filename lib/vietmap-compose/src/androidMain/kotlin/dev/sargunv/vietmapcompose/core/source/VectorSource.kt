package dev.sargunv.vietmapcompose.core.source

import dev.sargunv.vietmapcompose.core.util.correctedAndroidUri
import dev.sargunv.vietmapcompose.core.util.toMLNExpression
import dev.sargunv.vietmapcompose.expressions.ExpressionContext
import dev.sargunv.vietmapcompose.expressions.ast.Expression
import dev.sargunv.vietmapcompose.expressions.dsl.const
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.Feature
import vn.vietmap.vietmapsdk.style.sources.VectorSource as MLNVectorSource

public actual class VectorSource : Source {
  override val impl: MLNVectorSource

  internal constructor(source: MLNVectorSource) {
    impl = source
  }

  public actual constructor(id: String, uri: String) {
    impl = MLNVectorSource(id, uri.correctedAndroidUri())
  }

  public actual fun querySourceFeatures(
    sourceLayerIds: Set<String>,
    predicate: Expression<BooleanValue>,
  ): List<Feature> {
    return impl
      .querySourceFeatures(
        sourceLayerIds = sourceLayerIds.toTypedArray(),
        filter =
          predicate
            .takeUnless { it == const(true) }
            ?.compile(ExpressionContext.None)
            ?.toMLNExpression(),
      )
      .map { Feature.fromJson(it.toJson()) }
  }
}
