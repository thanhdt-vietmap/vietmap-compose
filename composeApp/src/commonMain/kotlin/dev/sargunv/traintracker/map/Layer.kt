package dev.sargunv.traintracker.map

import androidx.annotation.ColorInt

data class Layer(
    val id: String,
    val source: String,
    val type: Type,
    val below: String? = null,
    val above: String? = null,
    val index: Int? = null,
    val minZoom: Float? = null,
    val maxZoom: Float? = null,
) {
    init {
        require(below == null || above == null || index == null) {
            "Only one of below, above, or index can be set"
        }
    }

    sealed class Type {
        data class Line(
            val cap: String? = null,
            val join: String? = null,
            @ColorInt val color: Int? = null,
            val width: Float? = null,
        ) : Type()
    }
}