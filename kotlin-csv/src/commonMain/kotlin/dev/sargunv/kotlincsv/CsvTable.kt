package dev.sargunv.kotlincsv

public data class CsvTable(val header: List<String>, val records: Sequence<List<String>>)
