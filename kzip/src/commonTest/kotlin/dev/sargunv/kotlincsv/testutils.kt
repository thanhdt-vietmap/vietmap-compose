package dev.sargunv.kotlincsv

import kotlin.test.assertEquals
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString
import kotlinx.serialization.Serializable

fun load(name: String): Source {
  SystemFileSystem.list(Path(".")).forEach { println(it) }
  return SystemFileSystem.source(Path("src/commonTest/testdata/csv/$name.csv")).buffered()
}

fun assertBuffersEqual(expected: Source, actual: Source) {
  assertEquals(
    expected.readString().trimEnd { c -> c == '\n' },
    actual.readString().trimEnd { c -> c == '\n' },
  )
}

@Serializable data class OneString(val foo: String)

@Serializable data class Strings(val foo: String, val bar: String, val baz: String)

@Serializable data class StringsOutOfOrder(val bar: String, val baz: String, val foo: String)

@Serializable data class NullableStrings(val foo: String?, val bar: String?, val baz: String?)

@Serializable data class Ints(val foo: Int, val bar: Int, val baz: Int)

@Serializable data class NullableInts(val foo: Int?, val bar: Int?, val baz: Int?)

@Serializable
data class Enums(val foo: FooBarBaz, val bar: FooBarBaz, val baz: FooBarBaz) {
  enum class FooBarBaz {
    a,
    b,
    c,
    d,
  }
}

@Serializable data class NamingOne(val testFoo: String)

@Serializable
data class NamingNullable(val testFoo: String?, val testBar: String?, val testBaz: String?)
