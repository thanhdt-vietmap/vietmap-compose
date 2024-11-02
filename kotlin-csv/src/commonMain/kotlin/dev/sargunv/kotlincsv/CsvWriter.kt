package dev.sargunv.kotlincsv

import kotlinx.io.Sink
import kotlinx.io.writeString

class CsvWriter
private constructor(
  private val table: Sequence<List<String>>,
  private val sink: Sink,
  private val encoding: CsvEncoding = CsvEncoding(),
) {
  private val numColumns = table.firstOrNull()?.size ?: 0
  private var rowCount = 0

  companion object {
    fun of(table: CsvTable, sink: Sink, encoding: CsvEncoding = CsvEncoding()) =
      CsvWriter(sequenceOf(table.header) + table.records, sink, encoding)

    fun of(table: Sequence<List<String>>, sink: Sink, encoding: CsvEncoding = CsvEncoding()) =
      CsvWriter(table, sink, encoding)

    fun of(table: List<List<String>>, sink: Sink, encoding: CsvEncoding = CsvEncoding()) =
      of(table.asSequence(), sink, encoding)

    fun ofMaps(
      table: Sequence<Map<String, String>>,
      sink: Sink,
      encoding: CsvEncoding = CsvEncoding(),
    ) =
      of(
        sequence<List<String>> {
          val keys = table.firstOrNull()?.keys ?: return@sequence
          yield(keys.toList())
          for (row in table) {
            require(row.keys == keys) { "All rows must have the same keys" }
            yield(keys.map { row[it]!! })
          }
        },
        sink,
        encoding,
      )

    fun ofMaps(
      table: List<Map<String, String>>,
      sink: Sink,
      encoding: CsvEncoding = CsvEncoding(),
    ) = ofMaps(table.asSequence(), sink, encoding)
  }

  private inline fun Sink.writeChar(c: Char) {
    writeString(c.toString())
  }

  private fun writeField(field: String) {
    if (field.contains(encoding.delimiter) || field.contains(encoding.quote)) {
      sink.writeChar(encoding.quote)
      sink.writeString(
        field.replace(encoding.quote.toString(), encoding.quote.toString() + encoding.quote)
      )
      sink.writeChar(encoding.quote)
    } else {
      sink.writeString(field)
    }
  }

  private fun writeRecord(record: List<String>) {
    require(record.size == numColumns) { "Row $rowCount has incorrect number of columns" }
    rowCount++

    record.forEachIndexed { i, field ->
      if (i > 0) sink.writeChar(encoding.delimiter)
      writeField(field)
    }

    if (encoding.writeCrlf) sink.writeChar(encoding.carriageReturn)
    sink.writeChar(encoding.newline)
  }

  fun write() = sink.use { table.forEach(::writeRecord) }
}
