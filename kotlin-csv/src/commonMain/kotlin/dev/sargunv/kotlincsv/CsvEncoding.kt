package dev.sargunv.kotlincsv

data class CsvEncoding(
  val quote: Char = '"',
  val delimiter: Char = ',',
  val writeCrlf: Boolean = false,
) {
  // line delimiters prob shouldn't be configurable
  val newline = '\n'
  val carriageReturn = '\r'

  init {
    require(quote != delimiter) { "Quote and delimiter must be different" }
  }
}
