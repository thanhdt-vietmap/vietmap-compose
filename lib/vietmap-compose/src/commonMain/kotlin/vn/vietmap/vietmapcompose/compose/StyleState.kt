package vn.vietmap.vietmapcompose.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import vn.vietmap.vietmapcompose.core.Style
import vn.vietmap.vietmapcompose.core.source.AttributionLink
import vn.vietmap.vietmapcompose.core.source.Source

/** Remember a new [StyleState]. */
@Composable
public fun rememberStyleState(): StyleState {
  return remember { StyleState() }
}

/** Use this class to access information about the style, such as sources and layers. */
public class StyleState internal constructor() {
  private var style: Style? = null

  internal fun attach(style: Style?) {
    if (this.style != style) {
      this.style = style
    }
  }

  public fun queryAttributionLinks(): List<AttributionLink> {
    // TODO expose this as State somehow?
    return style?.getSources()?.flatMap { it.attributionLinks } ?: emptyList()
  }

  /**
   * Retrieves all sources from the style.
   *
   * @return A list of sources, or an empty list if the style is null or has no sources.
   */
  public fun getSources(): List<Source> = style?.getSources() ?: emptyList()

  /**
   * Retrieves a source by its [id].
   *
   * @param id The ID of the source to retrieve.
   * @return The source with the specified ID, or null if no such source exists.
   */
  public fun getSource(id: String): Source? = style?.getSource(id)
}
