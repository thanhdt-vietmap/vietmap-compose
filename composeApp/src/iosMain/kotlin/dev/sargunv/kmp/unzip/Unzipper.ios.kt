package dev.sargunv.kmp.unzip

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.io.Source
import kotlinx.io.readByteArray
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.posix.memcpy

@Suppress("unused") // called in Swift
@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray() = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
    }
}

@Suppress("unused") // called in Swift
@OptIn(ExperimentalForeignApi::class)
fun Source.toNSData() = readByteArray().usePinned {
    NSData.dataWithBytes(it.addressOf(0), it.get().size.toULong())
}