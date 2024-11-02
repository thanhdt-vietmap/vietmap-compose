package dev.sargunv.traintracker.csv

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CsvParserTest {
  private fun parseHeaderless(name: String): List<List<String>> {
    return CsvParser(load(name)).parseHeaderless().toList()
  }

  private fun parseToMaps(name: String): List<Map<String, String>> {
    return CsvParser(load(name)).parseToMaps().toList()
  }

  @Test
  fun allEmpty() {
    assertEquals(listOf(listOf(""), listOf("")), parseHeaderless("all-empty"))
  }

  @Test
  fun badHeaderLessFields() {
    assertFailsWith(CsvParser.CsvParseException::class) { parseToMaps("bad-header-less-fields") }
  }

  @Test
  fun badHeaderMoreFields() {
    assertFailsWith(CsvParser.CsvParseException::class) { parseToMaps("bad-header-more-fields") }
  }

  @Test
  fun badHeaderNoHeader() {
    assertFailsWith(CsvParser.CsvParseException::class) { parseToMaps("bad-header-no-header") }
  }

  @Test
  fun parseTable() {
    val (header, _) = CsvParser(load("simple-lf")).parse()
    assertEquals(listOf("foo", "bar", "baz"), header)
  }

  @Test
  fun badMissingQuote() {
    assertFailsWith(CsvParser.CsvParseException::class) { parseHeaderless("bad-missing-quote") }
  }

  @Test
  fun badQuotesWithUnescapedQuote() {
    assertFailsWith(CsvParser.CsvParseException::class) {
      parseHeaderless("bad-quotes-with-unescaped-quote")
    }
  }

  @Test
  fun badUnescapedQuote() {
    assertFailsWith(CsvParser.CsvParseException::class) { parseHeaderless("bad-unescaped-quote") }
  }

  @Test
  fun emptyField() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "", "3")),
      parseHeaderless("empty-field"),
    )
  }

  @Test
  fun emptyOneColumn() {
    assertEquals(listOf(listOf("foo"), listOf("")), parseHeaderless("empty-one-column"))
  }

  @Test
  fun headerNoRows() {
    assertEquals(emptyList(), parseToMaps("header-no-rows"))
  }

  @Test
  fun headerSimple() {
    assertEquals(
      listOf(mapOf("foo" to "1", "bar" to "2", "baz" to "3")),
      parseToMaps("header-simple"),
    )
  }

  @Test
  fun leadingSpace() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", " leading space", "3")),
      parseHeaderless("leading-space"),
    )
  }

  @Test
  fun oneColumn() {
    assertEquals(listOf(listOf("foo"), listOf("1")), parseHeaderless("one-column"))
  }

  @Test
  fun quotesEmpty() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "", "3")),
      parseHeaderless("quotes-empty"),
    )
  }

  @Test
  fun quotesWithComma() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "Luke, I am your father.", "3")),
      parseHeaderless("quotes-with-comma"),
    )
  }

  @Test
  fun quotesWithEscapedQuote() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "The \" must be escaped", "3")),
      parseHeaderless("quotes-with-escaped-quote"),
    )
  }

  @Test
  fun quotesWithNewline() {
    assertEquals(
      listOf(
        listOf("foo", "bar", "baz"),
        listOf("1", "No man is an island,\nEntire of itself", "3"),
      ),
      parseHeaderless("quotes-with-newline"),
    )
  }

  @Test
  fun quotesWithSpace() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "Field with spaces", "3")),
      parseHeaderless("quotes-with-space"),
    )
  }

  @Test
  fun simpleCrlf() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "2", "3")),
      parseHeaderless("simple-crlf"),
    )
  }

  @Test
  fun simpleLf() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "2", "3")),
      parseHeaderless("simple-lf"),
    )
  }

  @Test
  fun trailingNewline() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "2", "3")),
      parseHeaderless("trailing-newline"),
    )
  }

  @Test
  fun trailingNewlineOneField() {
    assertEquals(listOf(listOf("foo"), listOf("1")), parseHeaderless("trailing-newline-one-field"))
  }

  @Test
  fun trailingSpace() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "trailing space ", "3")),
      parseHeaderless("trailing-space"),
    )
  }

  @Test
  fun utf8() {
    assertEquals(
      listOf(listOf("foo", "bar", "baz"), listOf("1", "\uD83D\uDE0E", "3")),
      parseHeaderless("utf8"),
    )
  }
}
