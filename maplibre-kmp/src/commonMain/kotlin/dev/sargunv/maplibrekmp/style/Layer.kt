package dev.sargunv.maplibrekmp.style

import androidx.compose.ui.graphics.Color

public data class Layer(
  val id: String,
  val source: Source,
  val type: Type,
  val minZoom: Float? = null,
  val maxZoom: Float? = null,
) {
  public sealed class Type {
    public data class Line(
      val cap: Expression<String>? = null,
      val join: Expression<String>? = null,
      val color: Expression<Color>? = null,
      val width: Expression<Number>? = null,
    ) : Type()
  }
}
