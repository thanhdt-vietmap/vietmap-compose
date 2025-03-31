package dev.sargunv.vietmapcompose.expressions.value

import dev.sargunv.vietmapcompose.expressions.ast.StringLiteral

/** Type of a GeoJson feature, as returned by [Feature.type]. */
public enum class GeometryType(override val literal: StringLiteral) : EnumValue<GeometryType> {
  Point(StringLiteral.of("Point")),
  LineString(StringLiteral.of("LineString")),
  Polygon(StringLiteral.of("Polygon")),
  MultiPoint(StringLiteral.of("MultiPoint")),
  MultiLineString(StringLiteral.of("MultiLineString")),
  MultiPolygon(StringLiteral.of("MultiPolygon")),
}
