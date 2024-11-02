package dev.sargunv.traintracker.zip

import io.ktor.utils.io.streams.inputStream
import java.util.zip.ZipInputStream
import kotlinx.io.RawSource
import kotlinx.io.Source
import kotlinx.io.asSource
import kotlinx.io.buffered

actual class Unzipper {
  actual fun readArchive(source: Source, handleFile: (path: String, source: Source) -> Unit) {
    val zip = ZipInputStream(source.inputStream())
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
}
