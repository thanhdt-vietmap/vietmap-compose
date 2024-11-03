package dev.sargunv.kotlincsv

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.io.Buffer
import kotlinx.io.Source

class CsvWriterTest {
  private fun write(data: List<List<String>>): Source = Buffer().also { CsvWriter(it).write(data) }

  private fun writeMaps(data: List<Map<String, String>>): Source =
    Buffer().also { CsvWriter(it).writeMaps(data) }

  @Test
  fun allEmpty() {
    assertBuffersEqual(load("all-empty"), write(listOf(listOf(""), listOf(""))))
  }

  @Test
  fun badHeaderMoreFields() {
    assertFailsWith(IllegalArgumentException::class) {
      write(listOf(listOf("foo", "bar", "baz"), listOf("1", "2", "3", "4")))
    }
  }

  @Test
  fun badHeaderInconsistent() {
    assertFailsWith(IllegalArgumentException::class) {
      writeMaps(listOf(mapOf("foo" to "1"), mapOf("bar" to "2")))
    }
  }

  @Test
  fun simpleLfList() {
    assertBuffersEqual(
      load("simple-lf"),
      write(listOf(listOf("foo", "bar", "baz"), listOf("1", "2", "3"))),
    )
  }

  @Test
  fun simpleLfMap() {
    assertBuffersEqual(
      load("simple-lf"),
      writeMaps(listOf(mapOf("foo" to "1", "bar" to "2", "baz" to "3"))),
    )
  }

  @Test
  fun roundTrip() {
    val data = mutableListOf(listOf("foo", "bar", "baz"))
    for (i in 1..100) {
      data.add(listOf(i.toString(), (i * 2).toString(), (i * 3).toString()))
    }
    assertEquals(data.size, 101)
    assertEquals(data, CsvParser(write(data)).parseHeaderless().toList())
  }
}
