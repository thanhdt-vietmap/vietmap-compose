package dev.sargunv.maplibre.kmpp

import androidx.annotation.ColorInt

data class Layer(
    val id: String,
    val source: String,
    val type: Type,
    val minZoom: Float? = null,
    val maxZoom: Float? = null,
) {
    sealed class Type {
        data class Line(
            val cap: String? = null,
            val join: String? = null,
            @ColorInt val color: Int? = null,
            val width: Float? = null,
        ) : Type()
    }
}