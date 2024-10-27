package dev.sargunv.traintracker.csv

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CsvParserTest {
    private fun parseWithoutHeader(name: String): List<List<String>> {
        return CsvParser(load(name)).parseWithoutHeader().toList()
    }

    private fun parseWithHeader(name: String): List<Map<String, String>> {
        return CsvParser(load(name)).parseWithHeader().toList()
    }

    @Test
    fun allEmpty() {
        assertEquals(
            listOf(
                listOf(""),
                listOf(""),
            ),
            parseWithoutHeader("all-empty"),
        )
    }

    @Test
    fun badHeaderLessFields() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithHeader("bad-header-less-fields")
        }
    }

    @Test
    fun badHeaderMoreFields() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithHeader("bad-header-more-fields")
        }
    }

    @Test
    fun badHeaderNoHeader() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithHeader("bad-header-no-header")
        }
    }

    @Test
    fun badHeaderWrongHeader() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            CsvParser(load("bad-header-wrong-header")).parseWithHeader { header ->
                header.toSet() == setOf("foo", "bar", "baz")
            }.toList()
        }

        CsvParser(load("simple-lf")).parseWithHeader { header ->
            header.toSet() == setOf("foo", "bar", "baz")
        }.toList()
    }

    @Test
    fun badMissingQuote() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithoutHeader("bad-missing-quote")
        }
    }

    @Test
    fun badQuotesWithUnescapedQuote() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithoutHeader("bad-quotes-with-unescaped-quote")
        }
    }

    @Test
    fun badUnescapedQuote() {
        assertFailsWith(CsvParser.CsvParseException::class) {
            parseWithoutHeader("bad-unescaped-quote")
        }
    }

    @Test
    fun emptyField() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "", "3"),
            ),
            parseWithoutHeader("empty-field"),
        )
    }

    @Test
    fun emptyOneColumn() {
        assertEquals(
            listOf(
                listOf("foo"),
                listOf(""),
            ),
            parseWithoutHeader("empty-one-column"),
        )
    }

    @Test
    fun headerNoRows() {
        assertEquals(
            emptyList(),
            parseWithHeader("header-no-rows"),
        )
    }

    @Test
    fun headerSimple() {
        assertEquals(
            listOf(
                mapOf("foo" to "1", "bar" to "2", "baz" to "3"),
            ),
            parseWithHeader("header-simple"),
        )
    }

    @Test
    fun leadingSpace() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", " leading space", "3"),
            ),
            parseWithoutHeader("leading-space"),
        )
    }

    @Test
    fun oneColumn() {
        assertEquals(
            listOf(
                listOf("foo"),
                listOf("1"),
            ),
            parseWithoutHeader("one-column"),
        )
    }

    @Test
    fun quotesEmpty() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "", "3"),
            ),
            parseWithoutHeader("quotes-empty"),
        )
    }

    @Test
    fun quotesWithComma() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "Luke, I am your father.", "3"),
            ),
            parseWithoutHeader("quotes-with-comma"),
        )
    }

    @Test
    fun quotesWithEscapedQuote() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "The \" must be escaped", "3"),
            ),
            parseWithoutHeader("quotes-with-escaped-quote"),
        )
    }

    @Test
    fun quotesWithNewline() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "No man is an island,\nEntire of itself", "3"),
            ),
            parseWithoutHeader("quotes-with-newline"),
        )
    }

    @Test
    fun quotesWithSpace() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "Field with spaces", "3"),
            ),
            parseWithoutHeader("quotes-with-space"),
        )
    }

    @Test
    fun simpleCrlf() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "2", "3"),
            ),
            parseWithoutHeader("simple-crlf"),
        )
    }

    @Test
    fun simpleLf() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "2", "3"),
            ),
            parseWithoutHeader("simple-lf"),
        )
    }

    @Test
    fun trailingNewline() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "2", "3"),
            ),
            parseWithoutHeader("trailing-newline"),
        )
    }

    @Test
    fun trailingNewlineOneField() {
        assertEquals(
            listOf(
                listOf("foo"),
                listOf("1"),
            ),
            parseWithoutHeader("trailing-newline-one-field"),
        )
    }

    @Test
    fun trailingSpace() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "trailing space ", "3"),
            ),
            parseWithoutHeader("trailing-space"),
        )
    }

    @Test
    fun utf8() {
        assertEquals(
            listOf(
                listOf("foo", "bar", "baz"),
                listOf("1", "\uD83D\uDE0E", "3"),
            ),
            parseWithoutHeader("utf8"),
        )
    }
}