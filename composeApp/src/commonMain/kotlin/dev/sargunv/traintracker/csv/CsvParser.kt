package dev.sargunv.traintracker.csv

import kotlinx.io.Source

class CsvParser(
    private val input: Source,
    private val config: Config = Config(),
) {
  private val specialChars = with(config) { "$quote$comma$newline$carriageReturn" }
  private val data = StringBuilder()
  private val buffer = ByteArray(4096)

  private data class ReadResult<T>(
      val value: T,
      val newPos: Int,
  )

  class CsvParseException(message: String) : Exception(message)

  private fun charAt(pos: Int): Char? {
    while (!input.exhausted() && data.length <= pos) {
      val numBytesRead = input.readAtMostTo(buffer, 0, buffer.size)
      data.append(buffer.decodeToString(0, numBytesRead))
    }
    if (pos < data.length) return data[pos]
    if (input.exhausted()) return null
    throw IllegalStateException()
  }

  private fun readQuotedField(pos: Int): ReadResult<String>? {
    var cursor = pos

    // accept opening quote
    if (charAt(cursor) != config.quote) return null
    cursor++

    val result = StringBuilder()
    while (true) {
      // require content
      val c = charAt(cursor) ?: throw CsvParseException("Unterminated quoted value")
      if (c == config.quote) {
        val next = charAt(cursor + 1)
        if (next == config.quote) {
          // accept escaped quote
          result.append(config.quote)
          cursor += 2
        } else {
          // accept closing quote
          cursor++
          break
        }
      } else {
        // accept content
        result.append(c)
        cursor++
      }
    }
    return ReadResult(result.toString(), cursor)
  }

  private fun readNonQuotedField(pos: Int): ReadResult<String>? {
    val firstChar = charAt(pos) ?: return null
    if (firstChar == config.quote) return null // not a non-quoted field

    var cursor = pos
    val result = StringBuilder()

    while (true) {
      val c = charAt(cursor) ?: break
      if (c == config.quote) throw CsvParseException("Unexpected quote in non-quoted field")
      if (specialChars.contains(c)) break
      result.append(c)
      cursor++
    }

    return ReadResult(result.toString(), cursor)
  }

  private fun readField(pos: Int): ReadResult<String>? {
    return readQuotedField(pos) ?: readNonQuotedField(pos)
  }

  private fun readRecord(pos: Int): ReadResult<List<String>>? {
    val (firstField, newPos) = readField(pos) ?: return null

    var cursor = newPos
    val fields = mutableListOf(firstField)

    while (true) {
      val c = charAt(cursor) ?: break
      when (c) {
        config.carriageReturn,
        config.newline -> break

        config.comma -> {
          cursor++
          val fieldResult =
              readField(cursor) ?: throw CsvParseException("Expected field after comma")
          fields.add(fieldResult.value)
          cursor = fieldResult.newPos
        }

        else -> throw CsvParseException("Expected comma or end of line, got $c")
      }
    }

    return ReadResult(fields, cursor)
  }

  private fun readEndOfLine(pos: Int): ReadResult<Unit>? {
    val c = charAt(pos) ?: return ReadResult(Unit, pos)
    return when (c) {
      config.newline -> ReadResult(Unit, pos + 1)
      config.carriageReturn -> {
        if (charAt(pos + 1) == config.newline) ReadResult(Unit, pos + 2)
        else ReadResult(Unit, pos + 1)
      }

      else -> null
    }
  }

  fun parseWithoutHeader(): Sequence<List<String>> = sequence {
    input.use {
      val (firstRecord, pos) =
          readRecord(0) ?: throw CsvParseException("Expected at least one record")
      var cursor =
          readEndOfLine(pos)?.newPos
              ?: throw CsvParseException("Expected end of line, got '${charAt(pos)}'")
      val numColumns = firstRecord.size

      yield(firstRecord)

      while (true) {
        val (record, newPos) = readRecord(cursor) ?: break
        if (record.size != numColumns) {
          throw CsvParseException(
              "Expected $numColumns columns, got ${record.size} in record $record")
        }
        cursor =
            readEndOfLine(newPos)?.newPos
                ?: throw CsvParseException("Expected end of line, got '${charAt(newPos)}'")
        yield(record)
      }

      if (cursor < data.length || !input.exhausted()) {
        throw CsvParseException("Unexpected data at end of input")
      }
    }
  }

  fun parseWithHeader(
      checkHeader: (header: List<String>) -> Boolean = { true }
  ): Sequence<Map<String, String>> {
    val records = parseWithoutHeader().iterator()
    if (!records.hasNext()) throw CsvParseException("Expected a header")

    val header = records.next()
    if (!checkHeader(header)) throw CsvParseException("Header check failed")

    return records.asSequence().map { record -> header.zip(record).toMap() }
  }

  data class Config(
      val quote: Char = '"',
      val comma: Char = ',',
      val newline: Char = '\n',
      val carriageReturn: Char = '\r',
  )
}
