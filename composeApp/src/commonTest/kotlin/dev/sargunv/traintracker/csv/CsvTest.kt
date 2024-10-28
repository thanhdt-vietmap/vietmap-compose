@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.traintracker.csv

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals

@Serializable
data class Strings(
    val foo: String,
    val bar: String,
    val baz: String,
)

@Serializable
data class StringsOutOfOrder(
    val bar: String,
    val baz: String,
    val foo: String,
)

@Serializable
data class NullableStrings(
    val foo: String?,
    val bar: String?,
    val baz: String?,
)

@Serializable
data class Ints(
    val foo: Int,
    val bar: Int,
    val baz: Int,
)

@Serializable
data class NullableInts(
    val foo: Int?,
    val bar: Int?,
    val baz: Int?,
)

@Serializable
data class Enums(
    val foo: FooBarBaz,
    val bar: FooBarBaz,
    val baz: FooBarBaz,
) {
    enum class FooBarBaz {
        a, b, c, d
    }
}

class CsvTest {
    @Test
    fun decodeMultiline() {
        assertEquals(
            listOf(
                Strings(foo = "1", bar = "2", baz = "3"),
                Strings(foo = "4", bar = "5", baz = "6"),
                Strings(foo = "7", bar = "8", baz = "9"),
            ),
            Csv().decodeFromSource(serializer(), load("simple-multiline")),
        )
    }

    @Test
    fun decodeInts() {
        assertEquals(
            listOf(
                Ints(foo = 1, bar = 2, baz = 3),
            ),
            Csv().decodeFromSource(serializer(), load("simple-lf")),
        )
    }

    @Test
    fun decodeOutOfOrder() {
        assertEquals(
            listOf(
                StringsOutOfOrder(foo = "1", bar = "2", baz = "3"),
                StringsOutOfOrder(foo = "4", bar = "5", baz = "6"),
                StringsOutOfOrder(foo = "7", bar = "8", baz = "9"),
            ),
            Csv().decodeFromSource(serializer(), load("simple-multiline")),
        )
    }

    @Test
    fun decodeNoRows() {
        assertEquals(
            emptyList<Strings>(),
            Csv().decodeFromSource(serializer(), load("header-no-rows")),
        )
    }

    @Test
    fun decodeEmptyStrings() {
        assertEquals(
            listOf(
                Strings(foo = "1", bar = "", baz = "3"),
            ),
            Csv().decodeFromSource(serializer(), load("empty-field")),
        )
    }

    @Test
    fun decodeNullableStrings() {
        assertEquals(
            listOf(
                NullableStrings(foo = "1", bar = null, baz = "3"),
            ),
            Csv().decodeFromSource(serializer(), load("empty-field")),
        )
    }


    @Test
    fun decodeNullableInts() {
        assertEquals(
            listOf(
                NullableInts(foo = 1, bar = null, baz = 3),
            ),
            Csv().decodeFromSource(serializer(), load("empty-field")),
        )
    }

    @Test
    fun decodeEnumsByName() {
        assertEquals(
            listOf(
                Enums(foo = Enums.FooBarBaz.a, bar = Enums.FooBarBaz.b, baz = Enums.FooBarBaz.c),
            ),
            Csv().decodeFromSource(serializer(), load("simple-words")),
        )
    }

    @Test
    fun decodeEnumsByOrdinal() {
        assertEquals(
            listOf(
                Enums(foo = Enums.FooBarBaz.b, bar = Enums.FooBarBaz.c, baz = Enums.FooBarBaz.d),
            ),
            Csv().decodeFromSource(serializer(), load("simple-lf")),
        )
    }

    @Test
    fun decodeImplicitNulls() {
        assertEquals(
            listOf(
                NullableStrings(foo = "1", bar = null, baz = null),
            ),
            Csv().decodeFromSource(serializer(), load("one-column")),
        )
    }
}