package dev.sargunv.vietmapcompose.expressions.value

import dev.sargunv.vietmapcompose.expressions.ast.StringLiteral

/** How the text will be laid out. */
public enum class TextWritingMode(override val literal: StringLiteral) :
  EnumValue<TextWritingMode> {
  /**
   * If a text's language supports horizontal writing mode, symbols with point placement would be
   * laid out horizontally.
   */
  Horizontal(StringLiteral.of("horizontal")),

  /**
   * If a text's language supports vertical writing mode, symbols with point placement would be laid
   * out vertically.
   */
  Vertical(StringLiteral.of("vertical")),
}
