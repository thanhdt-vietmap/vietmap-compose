package dev.sargunv.maplibrecompose

import androidx.compose.ui.graphics.Color

public data class Layer(
  val id: String,
  val source: String,
  val type: Type,
  val below: String? = null,
  val above: String? = null,
  val index: Int? = null,
  val minZoom: Float? = null,
  val maxZoom: Float? = null,
) {
  init {
    require(below == null || above == null || index == null) {
      "Only one of below, above, or index can be set"
    }
  }

  public sealed class Type {
    public data class Line(
      val cap: Expression<String>? = null,
      val join: Expression<String>? = null,
      val color: Expression<Color>? = null,
      val width: Expression<Number>? = null,
    ) : Type()
  }
}
