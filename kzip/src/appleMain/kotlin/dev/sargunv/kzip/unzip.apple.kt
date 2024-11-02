package dev.sargunv.kzip

import cocoapods.zipzap.ZZArchive
import cocoapods.zipzap.ZZArchiveEntry
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.io.Source
import kotlinx.io.asSource
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

@OptIn(ExperimentalForeignApi::class)
public actual fun unzip(zipArchive: Source, handleFile: (path: String, content: Source) -> Unit) {
  ZZArchive(
      data =
        zipArchive.readByteArray().usePinned {
          NSData.dataWithBytes(it.addressOf(0), it.get().size.toULong())
        },
      options = null,
      error = null,
    )
    .entries
    .map { it as ZZArchiveEntry }
    .forEach { handleFile(it.fileName, it.newStreamWithError(null)!!.asSource().buffered()) }
}
