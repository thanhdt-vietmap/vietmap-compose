package dev.sargunv.vietmapcompose.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnitType
import dev.sargunv.vietmapcompose.compose.engine.ImageManager
import dev.sargunv.vietmapcompose.compose.engine.LocalStyleNode
import dev.sargunv.vietmapcompose.compose.engine.StyleNode
import dev.sargunv.vietmapcompose.expressions.ExpressionContext
import dev.sargunv.vietmapcompose.expressions.ast.BitmapLiteral
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.ast.Expression
import dev.sargunv.vietmapcompose.expressions.ast.PainterLiteral
import dev.sargunv.vietmapcompose.expressions.dsl.const
import dev.sargunv.vietmapcompose.expressions.value.ExpressionValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue

internal class LayerPropertyCompiler(
  private val styleNode: StyleNode,
  private val density: Density,
  private val layoutDirection: LayoutDirection,
  private val emScale: Expression<FloatValue>? = null,
  private val spScale: Expression<FloatValue>? = null,
) {
  private val context =
    object : ExpressionContext {
      private var seenTextUnitType: TextUnitType? = null

      override val emScale: Expression<FloatValue>
        get() {
          return this@LayerPropertyCompiler.emScale
            ?: when (seenTextUnitType) {
              null -> {
                seenTextUnitType = TextUnitType.Em
                const(1f)
              }

              TextUnitType.Em -> const(1f)
              else -> error("mixing EM and SP units is not supported in most expressions")
            }
        }

      override val spScale: Expression<FloatValue>
        get() {
          return this@LayerPropertyCompiler.spScale
            ?: when (seenTextUnitType) {
              null -> {
                seenTextUnitType = TextUnitType.Sp
                const(1f)
              }

              TextUnitType.Sp -> const(1f)
              else -> error("mixing SP and EM units is not supported in most expressions")
            }
        }

      override fun resolveBitmap(bitmap: BitmapLiteral): String {
        return styleNode.imageManager.acquireBitmap(bitmap.key())
      }

      override fun resolvePainter(painter: PainterLiteral): String {
        return styleNode.imageManager.acquirePainter(painter.key(density, layoutDirection))
      }

      fun reset() {
        seenTextUnitType = null
      }
    }

  @Composable
  operator fun <T : ExpressionValue> invoke(expression: Expression<T>): CompiledExpression<T> {
    DisposableEffect(this, expression) {
      onDispose {
        expression.visit {
          when (it) {
            is BitmapLiteral -> styleNode.imageManager.releaseBitmap(it.key())
            is PainterLiteral ->
              styleNode.imageManager.releasePainter(it.key(density, layoutDirection))

            else -> {}
          }
        }
      }
    }
    return remember(this, expression) {
      context.reset()
      expression.compile(context)
    }
  }

  private fun BitmapLiteral.key() = ImageManager.BitmapKey(value, sdf)

  private fun PainterLiteral.key(
    density: Density,
    layoutDirection: LayoutDirection,
  ): ImageManager.PainterKey = ImageManager.PainterKey(value, density, layoutDirection, size, sdf)
}

@Composable
internal fun rememberPropertyCompiler(
  emScale: Expression<FloatValue>? = null,
  spScale: Expression<FloatValue>? = null,
): LayerPropertyCompiler {
  val styleNode = LocalStyleNode.current
  val density = LocalDensity.current
  val layoutDirection = LocalLayoutDirection.current
  return remember(styleNode, density, layoutDirection, emScale, spScale) {
    LayerPropertyCompiler(styleNode, density, layoutDirection, emScale, spScale)
  }
}
