package dev.sargunv.traintracker.csv

import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal fun load(name: String): Source {
    SystemFileSystem.list(Path(".")).forEach { println(it) }
    return SystemFileSystem.source(Path("src/commonTest/testdata/csv/$name.csv")).buffered()
}