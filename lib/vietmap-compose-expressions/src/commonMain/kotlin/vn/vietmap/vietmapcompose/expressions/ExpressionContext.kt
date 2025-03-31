package vn.vietmap.vietmapcompose.expressions

import vn.vietmap.vietmapcompose.expressions.ast.BitmapLiteral
import vn.vietmap.vietmapcompose.expressions.ast.CompiledExpression
import vn.vietmap.vietmapcompose.expressions.ast.Expression
import vn.vietmap.vietmapcompose.expressions.ast.PainterLiteral
import vn.vietmap.vietmapcompose.expressions.value.FloatValue

/**
 * The context used while converting a high-level [Expression] to a low-level [CompiledExpression].
 *
 * It defines how to resolve certain expressions (TextUnit, bitmaps) to their VietMap counterparts.
 * VietMap Compose users should not need to implement this interface; it is used internally by the
 * VietMap Compose library.
 */
public interface ExpressionContext {
  /** The scale factor to convert EMs to the desired unit */
  public val emScale: Expression<FloatValue>

  /** The scale factor to convert SPs to the desired unit */
  public val spScale: Expression<FloatValue>

  /** @return the resolved identifier for the [bitmap]. */
  public fun resolveBitmap(bitmap: BitmapLiteral): String

  /** @return the resolved identifier for the [painter]. */
  public fun resolvePainter(painter: PainterLiteral): String

  /** A context where no complex types can be resolved. */
  public object None : ExpressionContext {
    override val emScale: Expression<FloatValue>
      get() = error("TextUnit not allowed in this context")

    override val spScale: Expression<FloatValue>
      get() = error("TextUnit not allowed in this context")

    override fun resolveBitmap(bitmap: BitmapLiteral): String =
      error("Bitmap not allowed in this context")

    override fun resolvePainter(painter: PainterLiteral): String =
      error("Painter not allowed in this context")
  }
}
