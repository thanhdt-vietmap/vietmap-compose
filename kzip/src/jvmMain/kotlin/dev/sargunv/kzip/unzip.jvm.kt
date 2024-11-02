package dev.sargunv.kzip

import kotlinx.io.RawSource
import kotlinx.io.Source
import kotlinx.io.asInputStream
import kotlinx.io.asSource
import kotlinx.io.buffered
import java.util.zip.ZipInputStream

public actual fun unzip(zipArchive: Source, handleFile: (path: String, content: Source) -> Unit) {
  val zip = ZipInputStream(zipArchive.asInputStream())
  generateSequence { zip.nextEntry }
    .forEach { entry ->
      if (entry.isDirectory) return@forEach
      object : RawSource by zip.asSource() {
          override fun close() = zip.closeEntry()
        }
        .buffered()
        .use { handleFile(entry.name, it) }
    }
}
