package vn.vietmap.vietmapcompose.core.source

import vn.vietmap.vietmapcompose.expressions.ast.Expression
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
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
