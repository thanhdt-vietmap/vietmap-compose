package dev.sargunv.traintracker.zip

import kotlinx.io.Source

expect class Unzipper() {
  fun readArchive(
    source: Source,
    handleFile: (path: String, content: Source) -> Unit = { _, content -> content.close() },
  )
}
