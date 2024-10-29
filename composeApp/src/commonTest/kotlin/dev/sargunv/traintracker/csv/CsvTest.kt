@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.traintracker.csv

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

@Serializable
data class NamingOne(
    val testFoo: String,
)

@Serializable
data class NamingNullable(
    val testFoo: String?,
    val testBar: String?,
    val testBaz: String?,
)

class CsvTest {
    @Test
    fun decodeMultiline() {
        assertEquals(
            listOf(
                Strings(foo = "1", bar = "2", baz = "3"),
                Strings(foo = "4", bar = "5", baz = "6"),
                Strings(foo = "7", bar = "8", baz = "9"),
            ),
            Csv.Default.decodeFromSource(serializer(), load("simple-multiline")),
        )
    }

    @Test
    fun decodeInts() {
        assertEquals(
            listOf(
                Ints(foo = 1, bar = 2, baz = 3),
            ),
            Csv.Default.decodeFromSource(serializer(), load("simple-lf")),
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
            Csv.Default.decodeFromSource(serializer(), load("simple-multiline")),
        )
    }

    @Test
    fun decodeNoRows() {
        assertEquals(
            emptyList<Strings>(),
            Csv.Default.decodeFromSource(serializer(), load("header-no-rows")),
        )
    }

    @Test
    fun decodeEmptyStrings() {
        assertEquals(
            listOf(
                Strings(foo = "1", bar = "", baz = "3"),
            ),
            Csv.Default.decodeFromSource(serializer(), load("empty-field")),
        )
    }

    @Test
    fun decodeNullableStrings() {
        assertEquals(
            listOf(
                NullableStrings(foo = "1", bar = null, baz = "3"),
            ),
            Csv.Default.decodeFromSource(serializer(), load("empty-field")),
        )
    }


    @Test
    fun decodeNullableInts() {
        assertEquals(
            listOf(
                NullableInts(foo = 1, bar = null, baz = 3),
            ),
            Csv.Default.decodeFromSource(serializer(), load("empty-field")),
        )
    }

    @Test
    fun decodeEnumsByName() {
        assertEquals(
            listOf(
                Enums(foo = Enums.FooBarBaz.a, bar = Enums.FooBarBaz.b, baz = Enums.FooBarBaz.c),
            ),
            Csv.Default.decodeFromSource(serializer(), load("simple-words")),
        )
    }

    @Test
    fun decodeEnumsByOrdinal() {
        assertEquals(
            listOf(
                Enums(foo = Enums.FooBarBaz.b, bar = Enums.FooBarBaz.c, baz = Enums.FooBarBaz.d),
            ),
            Csv.Default.decodeFromSource(serializer(), load("simple-lf")),
        )
    }

    @Test
    fun decodeTreatMissingColumnsAsNullFalse() {
        assertFailsWith<MissingFieldException>("") {
            Csv.Default.decodeFromSource<List<Enums>>(serializer(), load("one-column"))
        }
    }

    @Test
    fun decodeTreatMissingColumnsAsNullTrue() {
        assertEquals(
            listOf(
                NullableStrings(foo = "1", bar = null, baz = null),
            ),
            Csv(Csv.Config(treatMissingColumnsAsNull = true)).decodeFromSource(
                serializer(),
                load("one-column")
            ),
        )
    }

    @Test
    fun decodeNamingStrategy() {
        assertEquals(
            listOf(
                NamingOne(testFoo = "1"),
            ),
            Csv(Csv.Config(namingStrategy = CsvNamingStrategy.SnakeCase)).decodeFromSource(
                serializer(),
                load("snake-case")
            ),
        )
    }

    @Test
    fun decodeNamingStrategyMissingColumns() {
        assertEquals(
            listOf(
                NamingNullable(testFoo = "1", testBar = null, testBaz = null),
            ),
            Csv(
                Csv.Config(
                    namingStrategy = CsvNamingStrategy.SnakeCase,
                    treatMissingColumnsAsNull = true
                )
            ).decodeFromSource(
                serializer(),
                load("snake-case")
            ),
        )
    }
}