package dev.sargunv.traintracker.csv

import kotlin.test.Test
import kotlin.test.assertEquals

class CsvNamingStrategyTest {
    @Test
    fun testIdentityToCsv() {
        val strategy = CsvNamingStrategy.Identity
        assertEquals("fooBarBaz", strategy.toCsvName("fooBarBaz"))
    }

    @Test
    fun testIdentityFromCsv() {
        val strategy = CsvNamingStrategy.Identity
        assertEquals("fooBarBaz", strategy.fromCsvName("fooBarBaz"))
    }

    @Test
    fun testSnakeCaseToCsv() {
        val strategy = CsvNamingStrategy.SnakeCase
        assertEquals("foo_bar_baz", strategy.toCsvName("fooBarBaz"))
    }

    @Test
    fun testSnakeCaseFromCsv() {
        val strategy = CsvNamingStrategy.SnakeCase
        assertEquals("fooBarBaz", strategy.fromCsvName("foo_bar_baz"))
    }

    @Test
    fun testKebabCasetoCsv() {
        val strategy = CsvNamingStrategy.KebabCase
        assertEquals("foo-bar-baz", strategy.toCsvName("fooBarBaz"))
    }

    @Test
    fun testKebabCaseFromCsv() {
        val strategy = CsvNamingStrategy.KebabCase
        assertEquals("fooBarBaz", strategy.fromCsvName("foo-bar-baz"))
    }

    @Test
    fun testReversed() {
        val strategy = CsvNamingStrategy.SnakeCase.reversed()
        assertEquals("fooBarBaz", strategy.toCsvName("foo_bar_baz"))
        assertEquals("foo_bar_baz", strategy.fromCsvName("fooBarBaz"))
    }

    @Test
    fun testCompositeToCsv() {
        val strategy = CsvNamingStrategy.Composite(
            listOf(
                CsvNamingStrategy.KebabCase.reversed(),
                CsvNamingStrategy.SnakeCase
            )
        )
        assertEquals("foo_bar_baz", strategy.toCsvName("foo-bar-baz"))
    }

    @Test
    fun testCompositeFromCsv() {
        val strategy = CsvNamingStrategy.Composite(
            listOf(
                CsvNamingStrategy.KebabCase.reversed(),
                CsvNamingStrategy.SnakeCase
            )
        )
        assertEquals("foo-bar-baz", strategy.fromCsvName("foo_bar_baz"))
    }
}