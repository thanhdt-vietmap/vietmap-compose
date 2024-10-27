package dev.sargunv.traintracker.zip

import io.ktor.utils.io.streams.inputStream
import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.asOutputStream
import java.util.zip.ZipInputStream

class UnzipperImpl : Unzipper {
    override fun readArchive(
        source: Source,
        handleFile: (path: String) -> Sink,
        handleDirectory: (path: String) -> Unit,
    ) {
        val input = ZipInputStream(source.inputStream())
        generateSequence { input.nextEntry }
            .forEach { entry ->
                val path = entry.name
                if (entry.isDirectory) handleDirectory(path)
                else handleFile(path).asOutputStream().let {
                    input.copyTo(it)
                    it.close()
                }
            }
    }
}
