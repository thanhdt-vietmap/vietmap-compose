package vn.vietmap.vietmapcompose.expressions.value

import vn.vietmap.vietmapcompose.expressions.ast.StringLiteral

/** Direction of light source when map is rotated. */
public enum class IlluminationAnchor(override val literal: StringLiteral) :
  EnumValue<IlluminationAnchor> {

  /** The hillshade illumination is relative to the north direction. */
  Map(StringLiteral.of("map")),

  /** The hillshade illumination is relative to the top of the viewport. */
  Viewport(StringLiteral.of("viewport")),
}
