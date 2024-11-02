package dev.sargunv.kotlincsv

data class CsvTable(val header: List<String>, val records: Sequence<List<String>>)
