package vn.vietmap.vietmapcompose.expressions.dsl

import vn.vietmap.vietmapcompose.expressions.ast.Expression
import vn.vietmap.vietmapcompose.expressions.ast.FunctionCall
import vn.vietmap.vietmapcompose.expressions.value.BooleanValue
import vn.vietmap.vietmapcompose.expressions.value.CollatorValue
import vn.vietmap.vietmapcompose.expressions.value.ComparableValue
import vn.vietmap.vietmapcompose.expressions.value.EquatableValue
import vn.vietmap.vietmapcompose.expressions.value.ExpressionValue
import vn.vietmap.vietmapcompose.expressions.value.FloatValue
import vn.vietmap.vietmapcompose.expressions.value.GeoJsonValue
import vn.vietmap.vietmapcompose.expressions.value.MatchableValue
import vn.vietmap.vietmapcompose.expressions.value.StringValue
import kotlin.jvm.JvmName

/**
 * Selects the first output from the given [conditions] whose corresponding test condition evaluates
 * to `true`, or the [fallback] value otherwise.
 *
 * Example:
 * ```kt
 * switch(
 *   condition(
 *     test = feature.has("color1") and feature.has("color2"),
 *     output = interpolate(
 *       linear(),
 *       zoom(),
 *       1 to feature.get("color1").convertToColor(),
 *       20 to feature.get("color2").convertToColor()
 *     ),
 *   ),
 *   condition(
 *     test = feature.has("color"),
 *     output = feature.get("color").convertToColor(),
 *   ),
 *   fallback = const(Color.Red),
 * )
 * ```
 *
 * If the feature has both a "color1" and "color2" property, the result is an interpolation between
 * these two colors based on the zoom level. Otherwise, if the feature has a "color" property, that
 * color is returned. If the feature has none of the three, the color red is returned.
 */
public fun <T : ExpressionValue> switch(
  vararg conditions: Condition<T>,
  fallback: Expression<T>,
): Expression<T> =
  FunctionCall.of(
      "case",
      *conditions.foldToArgs { (test, output) ->
        add(test)
        add(output)
      },
      fallback,
    )
    .cast()

/** See [case] */
public data class Condition<T : ExpressionValue>
internal constructor(
  internal val test: Expression<BooleanValue>,
  internal val output: Expression<T>,
)

/** Create a [Condition], see [case] */
public fun <T : ExpressionValue> condition(
  test: Expression<BooleanValue>,
  output: Expression<T>,
): Condition<T> = Condition(test, output)

/**
 * Selects the output from the given [cases] whose label value matches the [input], or the
 * [fallback] value if no match is found.
 *
 * Each label must be unique. If the input type does not match the type of the labels, the result
 * will be the [fallback] value.
 *
 * Example:
 * ```kt
 * switch(
 *   input = feature.get("building_type").asString(),
 *   case(
 *     label = "residential",
 *     output = const(Color.Cyan),
 *   ),
 *   case(
 *     label = listOf("commercial", "industrial"),
 *     output = const(Color.Yellow),
 *   ),
 *   fallback = const(Color.Red),
 * )
 * ```
 *
 * If the feature has a property "building_type" with the value "residential", cyan is returned.
 * Otherwise, if the value of that property is either "commercial" or "industrial", yellow is
 * returned. If none of that is true, the fallback is returned, i.e. red.
 */
public fun <I : MatchableValue, O : ExpressionValue> switch(
  input: Expression<I>,
  vararg cases: Case<I, O>,
  fallback: Expression<O>,
): Expression<O> =
  FunctionCall.of(
      "match",
      input,
      *cases.foldToArgs { (label, output) ->
        add(label)
        add(output)
      },
      fallback,
      isLiteralArg = { i ->
        // label positions are odd, starting from 1 and not including the fallback
        i in 1..(cases.size * 2) && i % 2 == 1
      },
    )
    .cast()

/** See [switch] */
public data class Case<@Suppress("unused") I : MatchableValue, O : ExpressionValue>
internal constructor(internal val label: Expression<*>, internal val output: Expression<O>)

/** Create a [Case], see [switch] */
public fun <O : ExpressionValue> case(label: String, output: Expression<O>): Case<StringValue, O> =
  Case(const(label), output)

/** Create a [Case], see [switch] */
public fun <O : ExpressionValue> case(label: Number, output: Expression<O>): Case<FloatValue, O> =
  Case(const(label.toFloat()), output)

/** Create a [Case], see [switch] */
@JvmName("stringsCase")
public fun <O : ExpressionValue> case(
  label: List<String>,
  output: Expression<O>,
): Case<StringValue, O> = Case(const(label), output)

/** Create a [Case], see [switch] */
@JvmName("numbersCase")
public fun <O : ExpressionValue> case(
  label: List<Number>,
  output: Expression<O>,
): Case<FloatValue, O> = Case(const(label), output)

/**
 * Evaluates each expression in [values] in turn until the first non-null value is obtained, and
 * returns that value.
 */
public fun <T : ExpressionValue> coalesce(vararg values: Expression<T>): Expression<T> =
  FunctionCall.of("coalesce", *values).cast()

/** Returns whether this expression is equal to [other]. */
public infix fun Expression<EquatableValue>.eq(
  other: Expression<EquatableValue>
): Expression<BooleanValue> = FunctionCall.of("==", this, other).cast()

/**
 * Returns whether the [left] string expression is equal to the [right] string expression. An
 * optional [collator] (see [collator] function) can be specified to control locale-dependent string
 * comparisons.
 */
public fun eq(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of("==", left, right, collator).cast()

/** Returns whether this expression is not equal to [other]. */
public infix fun Expression<EquatableValue>.neq(
  other: Expression<EquatableValue>
): Expression<BooleanValue> = FunctionCall.of("!=", this, other).cast()

/**
 * Returns whether the [left] string expression is not equal to the [right] string expression. An
 * optional [collator] (see [collator]) can be specified to control locale-dependent string
 * comparisons.
 */
public fun neq(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of("!=", left, right, collator).cast()

/**
 * Returns whether this expression is strictly greater than [other].
 *
 * Strings are compared lexicographically (`"b" > "a"`).
 */
public infix fun <T> Expression<ComparableValue<T>>.gt(
  other: Expression<ComparableValue<T>>
): Expression<BooleanValue> = FunctionCall.of(">", this, other).cast()

/**
 * Returns whether the [left] string expression is strictly greater than the [right] string
 * expression. An optional [collator] (see [collator]) can be specified to control locale-dependent
 * string comparisons.
 *
 * Strings are compared lexicographically (`"b" > "a"`).
 */
public fun gt(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of(">", left, right, collator).cast()

/**
 * Returns whether this expression is strictly less than [other].
 *
 * Strings are compared lexicographically (`"a" < "b"`).
 */
public infix fun <T> Expression<ComparableValue<T>>.lt(
  other: Expression<ComparableValue<T>>
): Expression<BooleanValue> = FunctionCall.of("<", this, other).cast()

/**
 * Returns whether the [left] string expression is strictly less than the [right] string expression.
 * An optional [collator] (see [collator]) can be specified to control locale-dependent string
 * comparisons.
 *
 * Strings are compared lexicographically (`"a" < "b"`).
 */
public fun lt(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of("<", left, right, collator).cast()

/**
 * Returns whether this expression is greater than or equal to [other].
 *
 * Strings are compared lexicographically (`"b" >= "a"`).
 */
public infix fun <T> Expression<ComparableValue<T>>.gte(
  other: Expression<ComparableValue<T>>
): Expression<BooleanValue> = FunctionCall.of(">=", this, other).cast()

/**
 * Returns whether the [left] string expression is greater than or equal to the [right] string
 * expression. An optional [collator] (see [collator]) can be specified to control locale-dependent
 * string comparisons.
 *
 * Strings are compared lexicographically (`"b" >= "a"`).
 */
public fun gte(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of(">=", left, right, collator).cast()

/**
 * Returns whether this string expression is less than or equal to [other].
 *
 * Strings are compared lexicographically (`"a" <= "b"`).
 */
public infix fun <T> Expression<ComparableValue<T>>.lte(
  other: Expression<ComparableValue<T>>
): Expression<BooleanValue> = FunctionCall.of("<=", this, other).cast()

/**
 * Returns whether the [left] string expression is less than or equal to the [right] string
 * expression. An optional [collator] (see [collator]) can be specified to control locale-dependent
 * string comparisons.
 *
 * Strings are compared lexicographically (`"a" < "b"`).
 */
public fun lte(
  left: Expression<StringValue>,
  right: Expression<StringValue>,
  collator: Expression<CollatorValue>,
): Expression<BooleanValue> = FunctionCall.of("<=", left, right, collator).cast()

/** Returns whether all [expressions] are `true`. */
public fun all(vararg expressions: Expression<BooleanValue>): Expression<BooleanValue> =
  FunctionCall.of("all", *expressions).cast()

/** Returns whether both this and [other] expressions are `true`. */
public infix fun Expression<BooleanValue>.and(
  other: Expression<BooleanValue>
): Expression<BooleanValue> = all(this, other)

/** Returns whether any [expressions] are `true`. */
public fun any(vararg expressions: Expression<BooleanValue>): Expression<BooleanValue> =
  FunctionCall.of("any", *expressions).cast()

/** Returns whether any of this or the [other] expressions are `true`. */
public infix fun Expression<BooleanValue>.or(
  other: Expression<BooleanValue>
): Expression<BooleanValue> = any(this, other)

/** Negates this expression. */
@JvmName("notOperator")
public operator fun Expression<BooleanValue>.not(): Expression<BooleanValue> =
  FunctionCall.of("!", this).cast()

/**
 * Returns true if the evaluated feature is fully contained inside a boundary of the input geometry,
 * false otherwise. The input value can be a valid GeoJSON of type Polygon, MultiPolygon, Feature,
 * or FeatureCollection. Supported features for evaluation:
 * - Point: Returns false if a point is on the boundary or falls outside the boundary.
 * - LineString: Returns false if any part of a line falls outside the boundary, the line intersects
 *   the boundary, or a line's endpoint is on the boundary.
 */
public fun within(geometry: Expression<GeoJsonValue>): Expression<BooleanValue> =
  FunctionCall.of("within", geometry).cast()
