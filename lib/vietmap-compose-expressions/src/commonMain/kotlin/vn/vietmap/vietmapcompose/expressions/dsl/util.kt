package vn.vietmap.vietmapcompose.expressions.dsl

import vn.vietmap.vietmapcompose.expressions.ast.Expression

internal inline fun <T> Array<T>.foldToArgs(
  block: MutableList<Expression<*>>.(element: T) -> Unit
) =
  fold(mutableListOf<Expression<*>>()) { acc, element -> acc.apply { block(element) } }
    .toTypedArray()

internal inline fun <T> List<T>.foldToArgs(block: MutableList<Expression<*>>.(element: T) -> Unit) =
  fold(mutableListOf<Expression<*>>()) { acc, element -> acc.apply { block(element) } }
    .toTypedArray()
