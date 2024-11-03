package dev.sargunv.maplibrecompose

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
      val cap: String? = null,
      val join: String? = null,
      val color: Color? = null,
      val width: Float? = null,
    ) : Type()
  }
}

public data class Color(val red: UByte, val green: UByte, val blue: UByte, val alpha: Float = 1f) {
  public constructor(
    color: UInt
  ) : this(
    red = (color shr 16 and 0xFFu).toUByte(),
    green = (color shr 8 and 0xFFu).toUByte(),
    blue = (color and 0xFFu).toUByte(),
    alpha = (color shr 24 and 0xFFu).toFloat() / 255f,
  )
}
