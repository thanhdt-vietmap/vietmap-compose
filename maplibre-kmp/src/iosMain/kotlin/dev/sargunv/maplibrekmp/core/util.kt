package dev.sargunv.maplibrekmp.core

import cocoapods.MapLibre.MLNFeatureProtocol
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

internal fun ByteArray.toNSData(): NSData {
  return usePinned { NSData.dataWithBytes(it.addressOf(0), it.get().size.toULong()) }
}

internal fun MLNFeatureProtocol.toJson(): JsonObject {
  val map = this.geoJSONDictionary()
  return JsonElement.fromAny(map) as JsonObject
}

internal fun JsonElement.Companion.fromAny(any: Any?): JsonElement {
  return when (any) {
    null -> JsonNull
    is Boolean -> JsonPrimitive(any)
    is Number -> JsonPrimitive(any)
    is String -> JsonPrimitive(any)
    is List<*> -> JsonArray(any.map { fromAny(it) })
    is Map<*, *> -> JsonObject(any.entries.associate { (k, v) -> k as String to fromAny(v) })
    else -> error("Unsupported type: ${any::class.simpleName}")
  }
}
