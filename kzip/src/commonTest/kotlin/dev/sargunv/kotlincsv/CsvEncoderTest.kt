@file:OptIn(ExperimentalSerializationApi::class)

package dev.sargunv.kotlincsv

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.io.Buffer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer

class CsvEncoderTest {
  private inline fun <reified T> encode(data: List<T>, format: CsvFormat = CsvFormat.Csv): Buffer =
    Buffer().also { format.encodeToSink(serializer(), data, it) }

  @Test
  fun encodeMultiline() {
    assertBuffersEqual(
      load("simple-multiline"),
      encode(
        listOf(
          Strings(foo = "1", bar = "2", baz = "3"),
          Strings(foo = "4", bar = "5", baz = "6"),
          Strings(foo = "7", bar = "8", baz = "9"),
        )
      ),
    )
  }

  @Test
  fun encodeInts() {
    assertBuffersEqual(load("simple-lf"), encode(listOf(Ints(foo = 1, bar = 2, baz = 3))))
  }

  @Test
  fun encodeNoRows() {
    assertBuffersEqual(load("header-no-rows"), encode(emptyList<Strings>()))
  }

  @Test
  fun encodeEmptyStrings() {
    assertBuffersEqual(load("empty-field"), encode(listOf(Strings(foo = "1", bar = "", baz = "3"))))
  }

  @Test
  fun encodeNullableStrings() {
    assertBuffersEqual(
      load("empty-field"),
      encode(listOf(NullableStrings(foo = "1", bar = null, baz = "3"))),
    )
  }

  @Test
  fun encodeNullableInts() {
    assertBuffersEqual(
      load("empty-field"),
      encode(listOf(NullableInts(foo = 1, bar = null, baz = 3))),
    )
  }

  @Test
  fun encodeEnumsByName() {
    assertBuffersEqual(
      load("simple-words"),
      encode(
        listOf(Enums(foo = Enums.FooBarBaz.a, bar = Enums.FooBarBaz.b, baz = Enums.FooBarBaz.c))
      ),
    )
  }

  @Test
  fun encodeEnumsByOrdinal() {
    assertBuffersEqual(
      load("simple-lf"),
      encode(
        listOf(Enums(foo = Enums.FooBarBaz.b, bar = Enums.FooBarBaz.c, baz = Enums.FooBarBaz.d)),
        format = CsvFormat(CsvFormat.Config(writeEnumsByName = false)),
      ),
    )
  }

  @Test
  fun encodeNamingStrategy() {
    assertBuffersEqual(
      load("snake-case"),
      encode(
        listOf(NamingOne(testFoo = "1")),
        format = CsvFormat(CsvFormat.Config(namingStrategy = CsvNamingStrategy.SnakeCase)),
      ),
    )
  }

  @Test
  fun roundTrip() {
    val data = mutableListOf<Strings>()
    for (i in 1..100) {
      data.add(Strings(foo = i.toString(), bar = (i * 2).toString(), baz = (i * 3).toString()))
    }
    assertEquals(data.size, 100)
    assertEquals(
      data,
      CsvFormat.Csv.decodeFromString(serializer(), CsvFormat.Csv.encodeToString(serializer(), data)),
    )
  }
}
