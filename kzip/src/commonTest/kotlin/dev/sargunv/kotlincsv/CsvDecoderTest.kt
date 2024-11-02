@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.kotlincsv

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.serializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CsvDecoderTest {
  @Test
  fun decodeMultiline() {
    assertEquals(
      listOf(
        Strings(foo = "1", bar = "2", baz = "3"),
        Strings(foo = "4", bar = "5", baz = "6"),
        Strings(foo = "7", bar = "8", baz = "9"),
      ),
      CsvFormat.Csv.decodeFromSource(serializer(), load("simple-multiline")),
    )
  }

  @Test
  fun decodeInts() {
    assertEquals(
      listOf(Ints(foo = 1, bar = 2, baz = 3)),
      CsvFormat.Csv.decodeFromSource(serializer(), load("simple-lf")),
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
      CsvFormat.Csv.decodeFromSource(serializer(), load("simple-multiline")),
    )
  }

  @Test
  fun decodeNoRows() {
    assertEquals(
      emptyList<Strings>(),
      CsvFormat.Csv.decodeFromSource(serializer(), load("header-no-rows")),
    )
  }

  @Test
  fun decodeEmptyStrings() {
    assertEquals(
      listOf(Strings(foo = "1", bar = "", baz = "3")),
      CsvFormat.Csv.decodeFromSource(serializer(), load("empty-field")),
    )
  }

  @Test
  fun decodeNullableStrings() {
    assertEquals(
      listOf(NullableStrings(foo = "1", bar = null, baz = "3")),
      CsvFormat.Csv.decodeFromSource(serializer(), load("empty-field")),
    )
  }

  @Test
  fun decodeNullableInts() {
    assertEquals(
      listOf(NullableInts(foo = 1, bar = null, baz = 3)),
      CsvFormat.Csv.decodeFromSource(serializer(), load("empty-field")),
    )
  }

  @Test
  fun decodeEnumsByName() {
    assertEquals(
      listOf(Enums(foo = Enums.FooBarBaz.a, bar = Enums.FooBarBaz.b, baz = Enums.FooBarBaz.c)),
      CsvFormat.Csv.decodeFromSource(serializer(), load("simple-words")),
    )
  }

  @Test
  fun decodeEnumsByOrdinal() {
    assertEquals(
      listOf(Enums(foo = Enums.FooBarBaz.b, bar = Enums.FooBarBaz.c, baz = Enums.FooBarBaz.d)),
      CsvFormat.Csv.decodeFromSource(serializer(), load("simple-lf")),
    )
  }

  @Test
  fun decodeTreatMissingColumnsAsNullFalse() {
    assertFailsWith<MissingFieldException> {
      CsvFormat.Csv.decodeFromSource<List<Enums>>(serializer(), load("one-column"))
    }
  }

  @Test
  fun decodeTreatMissingColumnsAsNullTrue() {
    assertEquals(
      listOf(NullableStrings(foo = "1", bar = null, baz = null)),
      CsvFormat(CsvFormat.Config(treatMissingColumnsAsNull = true))
        .decodeFromSource(serializer(), load("one-column")),
    )
  }

  @Test
  fun decodeIgnoreUnknownKeysFalse() {
    // UnknownFieldException is internal
    assertFailsWith<SerializationException> {
      CsvFormat.Csv.decodeFromSource<List<OneString>>(serializer(), load("simple-lf"))
    }
  }

  @Test
  fun decodeIgnoreUnknownKeysTrue() {
    assertEquals(
      listOf(OneString(foo = "1")),
      CsvFormat(CsvFormat.Config(ignoreUnknownKeys = true))
        .decodeFromSource(serializer(), load("simple-lf")),
    )
  }

  @Test
  fun decodeNamingStrategy() {
    assertEquals(
      listOf(NamingOne(testFoo = "1")),
      CsvFormat(CsvFormat.Config(namingStrategy = CsvNamingStrategy.SnakeCase))
        .decodeFromSource(serializer(), load("snake-case")),
    )
  }

  @Test
  fun decodeNamingStrategyMissingColumns() {
    assertEquals(
      listOf(NamingNullable(testFoo = "1", testBar = null, testBaz = null)),
      CsvFormat(
          CsvFormat.Config(
            namingStrategy = CsvNamingStrategy.SnakeCase,
            treatMissingColumnsAsNull = true,
          )
        )
        .decodeFromSource(serializer(), load("snake-case")),
    )
  }
}
