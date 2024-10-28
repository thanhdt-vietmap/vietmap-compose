package dev.sargunv.traintracker.gtfs.db

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class LongColumnAdapterSerializer<T : Any>(
    private val adapter: ColumnAdapter<T, Long>,
) : KSerializer<T> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(adapter::class.qualifiedName!!, PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): T {
        return adapter.decode(decoder.decodeLong())
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeLong(adapter.encode(value))
    }
}