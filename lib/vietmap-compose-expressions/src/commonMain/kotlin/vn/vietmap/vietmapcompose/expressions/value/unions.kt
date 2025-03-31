package vn.vietmap.vietmapcompose.expressions.value

import vn.vietmap.vietmapcompose.expressions.ast.Expression
import vn.vietmap.vietmapcompose.expressions.dsl.eq
import vn.vietmap.vietmapcompose.expressions.dsl.format
import vn.vietmap.vietmapcompose.expressions.dsl.gt
import vn.vietmap.vietmapcompose.expressions.dsl.gte
import vn.vietmap.vietmapcompose.expressions.dsl.interpolate
import vn.vietmap.vietmapcompose.expressions.dsl.lt
import vn.vietmap.vietmapcompose.expressions.dsl.lte
import vn.vietmap.vietmapcompose.expressions.dsl.neq
import vn.vietmap.vietmapcompose.expressions.dsl.switch

/** Represents and [Expression] that resolves to a value that can be an input to [format]. */
public sealed interface FormattableValue : ExpressionValue

/**
 * Represents an [Expression] that resolves to a value that can be compared for equality. See [eq]
 * and [neq].
 */
public sealed interface EquatableValue : ExpressionValue

/** Union type for an [Expression] that resolves to a value that can be matched. See [switch]. */
public sealed interface MatchableValue : ExpressionValue

/**
 * Union type for an [Expression] that resolves to a value that can be ordered with other values of
 * its type. See [gt], [lt], [gte], and [lte].
 *
 * @param T the type of the value that can be compared against for ordering.
 */
public sealed interface ComparableValue<T> : ExpressionValue

/**
 * Union type for an [Expression] that resolves to a value that can be interpolated. See
 * [interpolate].
 *
 * @param T the type of values that can be interpolated between.
 */
public sealed interface InterpolatableValue<T> : ExpressionValue
