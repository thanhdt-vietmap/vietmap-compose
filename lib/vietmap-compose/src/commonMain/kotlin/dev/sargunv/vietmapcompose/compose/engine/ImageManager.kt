package dev.sargunv.vietmapcompose.compose.engine

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class ImageManager(private val node: StyleNode) {
  private val bitmapIds = IncrementingIdMap<BitmapKey>("bitmap")
  private val bitmapCounter = ReferenceCounter<BitmapKey>()

  private val painterIds = IncrementingIdMap<PainterKey>("painter")
  private val painterCounter = ReferenceCounter<PainterKey>()
  private val painterBitmaps = mutableMapOf<PainterKey, ImageBitmap>()

  internal fun acquireBitmap(key: BitmapKey): String {
    bitmapCounter.increment(key) {
      val id = bitmapIds.addId(key)
      node.logger?.i { "Adding bitmap $id" }
      node.style.addImage(id, key.bitmap, key.sdf)
    }
    return bitmapIds.getId(key)
  }

  internal fun releaseBitmap(key: BitmapKey) {
    bitmapCounter.decrement(key) {
      val id = bitmapIds.removeId(key)
      node.logger?.i { "Removing bitmap $id" }
      node.style.removeImage(id)
    }
  }

  private fun PainterKey.drawToBitmap(): ImageBitmap {
    val size =
      with(density) {
        size?.let { Size(it.width.toPx(), it.height.toPx()) }
          ?: painter.intrinsicSize.takeOrElse { Size(16.dp.toPx(), 16.dp.toPx()) }
      }
    val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
    CanvasDrawScope().draw(density, layoutDirection, Canvas(bitmap), size) {
      with(painter) { draw(size) }
    }
    return bitmap
  }

  internal fun acquirePainter(key: PainterKey): String {
    painterCounter.increment(key) {
      val id = painterIds.addId(key)
      node.logger?.i { "Adding painter $id" }
      key.drawToBitmap().let { bitmap ->
        painterBitmaps[key] = bitmap
        node.style.addImage(id, bitmap, key.sdf)
      }
    }
    return painterIds.getId(key)
  }

  internal fun releasePainter(key: PainterKey) {
    painterCounter.decrement(key) {
      val id = painterIds.removeId(key)
      node.logger?.i { "Removing painter $id" }
      painterBitmaps.remove(key)
      node.style.removeImage(id)
    }
  }

  internal data class BitmapKey(val bitmap: ImageBitmap, val sdf: Boolean)

  internal data class PainterKey(
    val painter: Painter,
    val density: Density,
    val layoutDirection: LayoutDirection,
    val size: DpSize?,
    val sdf: Boolean,
  )
}
