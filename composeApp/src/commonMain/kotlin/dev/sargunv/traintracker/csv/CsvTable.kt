package dev.sargunv.traintracker.csv

data class CsvTable(val header: List<String>, val records: Sequence<List<String>>)
