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
        name.replace(Regex("[A-Z]")) { "_${it.value.lowercase()}" }

    override fun fromCsvName(name: String) =
        name.replace(Regex("_([a-z])")) { it.groupValues[1].uppercase() }
  }

  object KebabCase : CsvNamingStrategy {
    override fun toCsvName(name: String) =
        name.replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }

    override fun fromCsvName(name: String) =
        name.replace(Regex("-([a-z])")) { it.groupValues[1].uppercase() }
  }

  class Composite(
      private val strategies: List<CsvNamingStrategy>,
  ) : CsvNamingStrategy {
    override fun toCsvName(name: String) =
        strategies.fold(name) { acc, strategy -> strategy.toCsvName(acc) }

    override fun fromCsvName(name: String) =
        strategies.reversed().fold(name) { acc, strategy -> strategy.fromCsvName(acc) }
  }
}

fun CsvNamingStrategy.reversed(): CsvNamingStrategy =
    object : CsvNamingStrategy {
      override fun toCsvName(name: String) = this@reversed.fromCsvName(name)

      override fun fromCsvName(name: String) = this@reversed.toCsvName(name)
    }
