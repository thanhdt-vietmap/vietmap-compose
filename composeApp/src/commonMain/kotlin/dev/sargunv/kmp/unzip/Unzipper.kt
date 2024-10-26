package dev.sargunv.kmp.unzip

import kotlinx.io.Sink
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.discardingSink

interface Unzipper {
    @Throws(Exception::class)
    fun readArchive(
        source: Source,
        handleFile: (path: String) -> Sink = { discardingSink().buffered() },
        handleDirectory: (path: String) -> Unit = { },
    )
}