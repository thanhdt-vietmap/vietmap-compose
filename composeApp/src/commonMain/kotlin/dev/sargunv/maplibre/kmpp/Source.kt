package dev.sargunv.maplibre.kmpp

sealed class Source {
    data class GeoJson(
        val url: String,
        val tolerance: Float? = null,
    ) : Source()
}