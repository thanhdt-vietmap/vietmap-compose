package vn.vietmap.vietmapcompose.core.source

public actual sealed class Source {
  internal abstract val impl: Nothing

  internal actual val id: String
    get() = TODO()

  public actual val attributionLinks: List<AttributionLink>
    get() {
      TODO()
    }

  override fun toString(): String = "${this::class.simpleName}(id=\"$id\")"
}
