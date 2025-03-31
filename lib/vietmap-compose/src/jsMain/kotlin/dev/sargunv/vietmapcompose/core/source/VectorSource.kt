package dev.sargunv.vietmapcompose.core.source

import dev.sargunv.vietmapcompose.expressions.ast.Expression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.Feature

public actual class VectorSource actual constructor(id: String, uri: String) : Source() {
  override val impl: Nothing = TODO()

  public actual fun querySourceFeatures(
    sourceLayerIds: Set<String>,
    predicate: Expression<BooleanValue>,
  ): List<Feature> {
    TODO()
  }
}
