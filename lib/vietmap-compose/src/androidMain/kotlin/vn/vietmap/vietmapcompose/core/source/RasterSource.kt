package vn.vietmap.vietmapcompose.core.source

import vn.vietmap.vietmapcompose.core.util.correctedAndroidUri
import vn.vietmap.vietmapsdk.style.sources.RasterSource as MLNRasterSource

public actual class RasterSource : Source {
  override val impl: MLNRasterSource

  internal constructor(source: MLNRasterSource) {
    impl = source
  }

  public actual constructor(id: String, uri: String, tileSize: Int) {
    impl = MLNRasterSource(id, uri.correctedAndroidUri(), tileSize)
  }
}
