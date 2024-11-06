package dev.sargunv.maplibrekmp.style

public sealed class Source {
  public abstract val id: String

  public data class GeoJson(
    override val id: String,
    val url: String,
    val tolerance: Float? = null,
  ) : Source()
}
