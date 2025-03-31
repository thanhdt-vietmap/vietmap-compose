package vn.vietmap.vietmapcompose.core.source

import vn.vietmap.vietmapcompose.expressions.ast.Expression
import vn.vietmap.vietmapcompose.expressions.dsl.const
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import io.github.dellisd.spatialk.geojson.Feature

/**
 * A map data source of tiled vector data.
 *
 * @param id Unique identifier for this source
 * @param uri URI pointing to a JSON file that conforms to the
 *   [TileJSON specification](https://github.com/mapbox/tilejson-spec/)
 */
public expect class VectorSource(id: String, uri: String) : Source {
  /**
   * Returns a list of features from the vector source, limited to source layers with the given
   * [sourceLayerIds] and filtered by the given [predicate].
   *
   * @param sourceLayerIds A set of source layer IDs to query features from.
   * @param predicate An expression used to filter the features. If not specified, all features from
   *   the vector source are returned.
   * @return A list of features that match the query, or an empty list if the [sourceLayerIds] is
   *   empty or no features are found.
   */
  public fun querySourceFeatures(
    sourceLayerIds: Set<String>,
    predicate: Expression<BooleanValue> = const(true),
  ): List<Feature>
}
