package dev.sargunv.kotlincsv

public interface CsvNamingStrategy {
  public fun toCsvName(name: String): String

  public fun fromCsvName(name: String): String

  public fun reversed(): CsvNamingStrategy =
    object : CsvNamingStrategy {
      override fun toCsvName(name: String) = this@CsvNamingStrategy.fromCsvName(name)

      override fun fromCsvName(name: String) = this@CsvNamingStrategy.toCsvName(name)
    }

  public object Identity : CsvNamingStrategy {
    override fun toCsvName(name: String): String = name

    override fun fromCsvName(name: String): String = name
  }

  public object SnakeCase : CsvNamingStrategy {
    override fun toCsvName(name: String): String =
      name.replace(Regex("[A-Z]")) { "_${it.value.lowercase()}" }

    override fun fromCsvName(name: String): String =
      name.replace(Regex("_([a-z])")) { it.groupValues[1].uppercase() }
  }

  public object KebabCase : CsvNamingStrategy {
    override fun toCsvName(name: String): String =
      name.replace(Regex("[A-Z]")) { "-${it.value.lowercase()}" }

    override fun fromCsvName(name: String): String =
      name.replace(Regex("-([a-z])")) { it.groupValues[1].uppercase() }
  }

  public class Composite(private val strategies: List<CsvNamingStrategy>) : CsvNamingStrategy {
    override fun toCsvName(name: String): String =
      strategies.fold(name) { acc, strategy -> strategy.toCsvName(acc) }

    override fun fromCsvName(name: String): String =
      strategies.reversed().fold(name) { acc, strategy -> strategy.fromCsvName(acc) }
  }
}
