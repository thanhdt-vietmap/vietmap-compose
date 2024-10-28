package dev.sargunv.traintracker.csv

interface CsvNamingStrategy {
    fun toCsvName(name: String): String
    fun fromCsvName(name: String): String

    object Identity : CsvNamingStrategy {
        override fun toCsvName(name: String) = name
        override fun fromCsvName(name: String) = name
    }

    object SnakeCase : CsvNamingStrategy {
        override fun toCsvName(name: String) =
            name.replace(Regex("[A-Z]")) { "_${it.value.lowercase()}".drop(1) }

        override fun fromCsvName(name: String) =
            name.replace(Regex("_([a-z])")) { it.groupValues[1].uppercase() }
    }
}