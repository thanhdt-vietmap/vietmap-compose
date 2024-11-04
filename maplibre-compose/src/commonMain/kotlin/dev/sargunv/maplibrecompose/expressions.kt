package dev.sargunv.maplibrecompose

@Suppress("MemberVisibilityCanBePrivate")
public object ExpressionDsl {

  // types: https://maplibre.org/maplibre-style-spec/types/
  // minus point and padding, which don't seem to be used in expressions

  public fun const(string: String): Expression<String> = Expression(string)

  public fun const(number: Number): Expression<Number> = Expression(number)

  public fun const(bool: Boolean): Expression<Boolean> = Expression(bool)

  public fun const(`null`: Nothing?): Expression<Nothing?> = Expression(`null`)

  public fun colorRgb(
    red: UByte,
    green: UByte,
    blue: UByte,
    alpha: Float? = null,
  ): Expression<TColor> {
    require(alpha == null || alpha in 0f..1f) { "alpha must be in the range 0..1" }
    return Colors.rgb(red, green, blue, alpha)
  }

  public fun colorHsl(
    hue: Int,
    saturation: Int,
    lightness: Int,
    alpha: Float? = null,
  ): Expression<TColor> {
    require(hue in 0..360) { "hue must be in the range 0..360" }
    require(saturation in 0..100) { "saturation must be in the range 0..100" }
    require(lightness in 0..100) { "lightness must be in the range 0..100" }
    require(alpha == null || alpha in 0f..1f) { "alpha must be in the range 0..1" }
    return Colors.hsl(hue, saturation, lightness, alpha)
  }

  public fun color(value: UInt): Expression<TColor> =
    colorRgb(
      (value shr 16).toUByte(),
      (value shr 8).toUByte(),
      value.toUByte(),
      (value shr 24).toFloat() / 255f,
    )

  // expressions: https://maplibre.org/maplibre-style-spec/expressions/

  /**
   * Binds expressions to named variables, which can then be referenced in the result expression
   * using [var].
   */
  public fun <Input, Output> let(
    name: String,
    value: Expression<Input>,
    expression: Expression<Output>,
  ): Expression<Output> = call("let", const(name), value, expression)

  /** References variable bound using [let]. */
  public fun <T> `var`(name: String): Expression<T> = call("var", const(name))

  /** Produces a literal array value. */
  public fun <T> literal(values: List<Expression<T>>): Expression<List<T>> =
    call("literal", Expression(values))

  /** Produces a literal object value. */
  public fun literal(values: Map<String, Expression<*>>): Expression<Map<String, Expression<*>>> =
    call("literal", Expression(values))

  /**
   * Asserts that the input is an array (optionally with a specific item type and length). If, when
   * the input expression is evaluated, it is not of the asserted type, then this assertion will
   * cause the whole expression to be aborted.
   */
  public fun <T> array(
    value: Expression<*>,
    type: Expression<String>? = null,
    length: Expression<Number>? = null,
  ): Expression<Array<T>> {
    val args = buildList {
      type?.let { add(const("array")) }
      length?.let { add(const("array")) }
    }
    return call("array", value, *args.toTypedArray())
  }

  /** Returns a string describing the type of the given value. */
  public fun `typeof`(expression: Expression<*>): Expression<String> = call("typeof", expression)

  /**
   * Asserts that the input value is a string. If multiple values are provided, each one is
   * evaluated in order until a string is obtained. If none of the inputs are strings, the
   * expression is an error.
   */
  public fun string(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<String> =
    call("string", value, *fallbacks)

  /**
   * Asserts that the input value is a number. If multiple values are provided, each one is
   * evaluated in order until a number is obtained. If none of the inputs are numbers, the
   * expression is an error.
   */
  public fun number(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Number> =
    call("number", value, *fallbacks)

  /**
   * Asserts that the input value is a boolean. If multiple values are provided, each one is
   * evaluated in order until a boolean is obtained. If none of the inputs are booleans, the
   * expression is an error.
   */
  public fun boolean(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Boolean> =
    call("boolean", value, *fallbacks)

  /**
   * Asserts that the input value is an object. If multiple values are provided, each one is
   * evaluated in order until an object is obtained. If none of the inputs are objects, the
   * expression is an error.
   */
  public fun `object`(
    value: Expression<*>,
    vararg fallbacks: Expression<*>,
  ): Expression<Map<String, *>> = call("object", value, *fallbacks)

  /**
   * Returns a collator for use in locale-dependent comparison operations. The [caseSensitive] and
   * [diacriticSensitive] options default to false. The [locale] argument specifies the IETF
   * language tag of the locale to use. If none is provided, the default locale is used. If the
   * requested locale is not available, the collator will use a system-defined fallback locale. Use
   * [resolvedLocale] to test the results of locale fallback behavior.
   */
  public fun collator(
    caseSensitive: Expression<Boolean>? = null,
    diacriticSensitive: Expression<Boolean>? = null,
    locale: Expression<String>? = null,
  ): Expression<TCollator> =
    call(
      "collator",
      options {
        if (caseSensitive != null) put("case-sensitive", caseSensitive)
        if (diacriticSensitive != null) put("diacritic-sensitive", diacriticSensitive)
        if (locale != null) put("locale", locale)
      },
    )

  /**
   * Returns a formatted string for displaying mixed-format text in the text-field property. The
   * input may contain a string literal or expression, including an 'image' expression. Strings may
   * be followed by a style override object that supports the following properties:
   */
  public fun format(vararg sections: Pair<Expression<*>, FormatStyle>): Expression<TFormatted> {
    val args =
      sections.flatMap { (value, style) ->
        listOf(
          value,
          options {
            if (style.textFont != null) put("text-font", style.textFont)
            if (style.textColor != null) put("text-color", style.textColor)
            if (style.fontScale != null) put("font-scale", style.fontScale)
          },
        )
      }
    return call("format", *args.toTypedArray())
  }

  public data class FormatStyle(
    val textFont: Expression<String>? = null,
    val textColor: Expression<String>? = null,
    val fontScale: Expression<Number>? = null,
  )

  /**
   * Returns an image type for use in [iconImage], *-pattern entries and as a section in the
   * [format] expression. If set, the image argument will check that the requested image exists in
   * the style and will return either the resolved image name or null, depending on whether or not
   * the image is currently in the style. This validation process is synchronous and requires the
   * image to have been added to the style before requesting it in the image argument.
   */
  public fun image(value: Expression<String>): Expression<TResolvedImage> = call("image", value)

  /**
   * Converts the input number into a string representation using the providing formatting rules. If
   * set, the locale argument specifies the locale to use, as a BCP 47 language tag. If set, the
   * currency argument specifies an ISO 4217 code to use for currency-style formatting. If set, the
   * min-fraction-digits and max-fraction-digits arguments specify the minimum and maximum number of
   * fractional digits to include.
   */
  public fun numberFormat(
    number: Expression<Number>,
    locale: Expression<String>? = null,
    currency: Expression<String>? = null,
    minFractionDigits: Expression<Number>? = null,
    maxFractionDigits: Expression<Number>? = null,
  ): Expression<String> =
    call(
      "number-format",
      number,
      options {
        if (locale != null) put("locale", locale)
        if (currency != null) put("currency", currency)
        if (minFractionDigits != null) put("min-fraction-digits", minFractionDigits)
        if (maxFractionDigits != null) put("max-fraction-digits", maxFractionDigits)
      },
    )

  /**
   * Converts the input value to a string. If the input is null, the result is "". If the input is a
   * boolean, the result is "true" or "false". If the input is a number, it is converted to a string
   * as specified by the "NumberToString" algorithm of the ECMAScript Language Specification. If the
   * input is a color, it is converted to a string of the form "rgba(r,g,b,a)", where r, g, and b
   * are numerals ranging from 0 to 255, and a ranges from 0 to 1. Otherwise, the input is converted
   * to a string in the format specified by the JSON.stringify function of the ECMAScript Language
   * Specification.
   */
  public fun toString(value: Expression<*>): Expression<String> = call("to-string", value)

  /**
   * Converts the input value to a number, if possible. If the input is null or false, the result
   * is 0. If the input is true, the result is 1. If the input is a string, it is converted to a
   * number as specified by the "ToNumber Applied to the String Type" algorithm of the ECMAScript
   * Language Specification. If multiple values are provided, each one is evaluated in order until
   * the first successful conversion is obtained. If none of the inputs can be converted, the
   * expression is an error.
   */
  public fun toNumber(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<Number> =
    call("to-number", value, *fallbacks)

  /**
   * Converts the input value to a boolean. The result is false when then input is an empty string,
   * 0, false, null, or NaN; otherwise it is true.
   */
  public fun toBoolean(value: Expression<*>): Expression<Boolean> = call("to-boolean", value)

  /**
   * Converts the input value to a color. If multiple values are provided, each one is evaluated in
   * order until the first successful conversion is obtained. If none of the inputs can be
   * converted, the expression is an error.
   */
  public fun toColor(value: Expression<*>, vararg fallbacks: Expression<*>): Expression<TColor> =
    call("to-color", value, *fallbacks)

  /** Retrieves an item from an array. */
  public fun <T> at(index: Expression<Number>, array: Expression<List<T>>): Expression<T> =
    call("at", index, array)

  /** Determines whether an item exists in an array or a substring exists in a string. */
  public fun `in`(needle: Expression<*>, haystack: Expression<*>): Expression<Boolean> =
    call("in", needle, haystack)

  /**
   * Returns the first position at which an item can be found in an array or a substring can be
   * found in a string, or -1 if the input cannot be found. Accepts an optional index from where to
   * begin the search. In a string, a UTF-16 surrogate pair counts as a single position.
   */
  public fun indexOf(
    value: Expression<*>,
    array: Expression<List<*>>,
    start: Expression<Number>? = null,
  ): Expression<Number> {
    val args = buildList {
      add(value)
      add(array)
      start?.let { add(it) }
    }
    return call("index-of", *args.toTypedArray())
  }

  /**
   * Returns an item from an array or a substring from a string from a specified start index, or
   * between a start index and an end index if set. The return value is inclusive of the start index
   * but not of the end index. In a string, a UTF-16 surrogate pair counts as a single position.
   */
  public fun <T> slice(
    value: Expression<*>,
    start: Expression<Number>,
    length: Expression<Number>? = null,
  ): Expression<T> {
    val args = buildList {
      add(value)
      add(start)
      length?.let { add(it) }
    }
    return call("slice", *args.toTypedArray())
  }

  /**
   * Retrieves a property value from the current feature's properties, or from another object if a
   * second argument is provided. Returns null if the requested property is missing.
   */
  public fun <T> get(
    key: Expression<String>,
    obj: Expression<Map<String, *>>? = null,
  ): Expression<T> {
    val args = obj?.let { listOf(key, it) } ?: listOf(key)
    return call("get", *args.toTypedArray())
  }

  /**
   * Tests for the presence of an property value in the current feature's properties, or from
   * another object if a second argument is provided.
   */
  public fun has(
    key: Expression<String>,
    obj: Expression<Map<String, *>>? = null,
  ): Expression<Boolean> {
    val args = obj?.let { listOf(key, it) } ?: listOf(key)
    return call("has", *args.toTypedArray())
  }

  /**
   * Gets the length of an array or string. In a string, a UTF-16 surrogate pair counts as a single
   * position.
   */
  public fun length(value: Expression<*>): Expression<Number> = call("length", value)

  // TODO above are in the right order from the docs, below are not

  public fun <Output> interpolate(
    type: Expression<TInterpolationType>,
    input: Expression<Number>,
    vararg stops: Pair<Number, Expression<Output>>,
  ): Expression<Output> {
    val args = stops.sortedBy { it.first.toDouble() }.flatMap { listOf(const(it.first), it.second) }
    return call("interpolate", type, input, *args.toTypedArray())
  }

  public fun linear(): Expression<TInterpolationType> = call("linear")

  public fun exponential(base: Expression<Number>): Expression<TInterpolationType> =
    call("exponential", base)

  public fun cubicBezier(
    x1: Expression<Number>,
    y1: Expression<Number>,
    x2: Expression<Number>,
    y2: Expression<Number>,
  ): Expression<TInterpolationType> = call("cubic-bezier", x1, y1, x2, y2)

  public fun zoom(): Expression<Number> = call("zoom")

  public fun rgba(
    red: Expression<Number>,
    green: Expression<Number>,
    blue: Expression<Number>,
    alpha: Expression<Number>,
  ): Expression<TColor> = call("rgba", red, green, blue, alpha)

  public fun rgb(
    red: Expression<Number>,
    green: Expression<Number>,
    blue: Expression<Number>,
  ): Expression<TColor> = call("rgb", red, green, blue)

  // utils

  @Suppress("UNCHECKED_CAST")
  private fun <Return> call(function: String, vararg args: Expression<*>) =
    Expression(listOf(const(function), *args)) as Expression<Return>

  private inline fun options(block: MutableMap<String, Expression<*>>.() -> Unit) =
    Expression(mutableMapOf<String, Expression<*>>().apply(block))
}

public fun <T> useExpressions(block: ExpressionDsl.() -> T): T = block(ExpressionDsl)

public interface Expression<T> {
  public val value: Any?

  public companion object {
    public operator fun invoke(string: String): Expression<String> = ExpressionImpl(string)

    public operator fun invoke(number: Number): Expression<Number> = ExpressionImpl(number)

    public operator fun invoke(bool: Boolean): Expression<Boolean> = ExpressionImpl(bool)

    public operator fun invoke(nil: Nothing? = null): Expression<Nothing?> = ExpressionImpl(nil)

    public operator fun invoke(list: List<Expression<*>>): Expression<List<*>> =
      ExpressionImpl(list.map { it.value })

    public operator fun invoke(map: Map<String, Expression<*>>): Expression<Map<String, *>> =
      ExpressionImpl(map.entries.associate { (key, value) -> key to value.value })
  }
}

private class ExpressionImpl<T>(override val value: Any?) : Expression<T>

public expect class Color : Expression<TColor>

public expect object Colors {
  public val white: Color
  public val silver: Color
  public val gray: Color
  public val black: Color
  public val red: Color
  public val maroon: Color
  public val yellow: Color
  public val green: Color
  public val blue: Color
  public val purple: Color

  public fun rgb(red: UByte, green: UByte, blue: UByte, alpha: Float? = null): Color

  public fun hsl(hue: Int, saturation: Int, lightness: Int, alpha: Float? = null): Color
}

// token types for expression type safety; these should never be instantiated
// based on non primitive types from https://maplibre.org/maplibre-style-spec/types/
// point and padding don't seem to be used in expressions, so I didn't include them

public sealed interface TColor

public sealed interface TFormatted

public sealed interface TResolvedImage

// also some extra tokens invented here

public sealed interface TCollator

public sealed interface TInterpolationType

// enum constants
