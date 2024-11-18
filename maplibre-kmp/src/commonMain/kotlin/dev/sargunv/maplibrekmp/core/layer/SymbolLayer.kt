package dev.sargunv.maplibrekmp.core.layer

import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrekmp.core.source.Source
import dev.sargunv.maplibrekmp.expression.Expression
import dev.sargunv.maplibrekmp.expression.Point
import dev.sargunv.maplibrekmp.expression.TResolvedImage

internal expect class SymbolLayer(id: String, source: Source) : Layer {
  fun setSymbolPlacement(expression: Expression<String>)

  fun setSymbolSpacing(expression: Expression<Number>)

  fun setSymbolAvoidEdges(expression: Expression<Boolean>)

  fun setSymbolSortKey(expression: Expression<Number>)

  fun setSymbolZOrder(expression: Expression<String>)

  fun setIconAllowOverlap(expression: Expression<Boolean>)

  fun setIconOverlap(expression: Expression<String>)

  fun setIconIgnorePlacement(expression: Expression<Boolean>)

  fun setIconOptional(expression: Expression<Boolean>)

  fun setIconRotationAlignment(expression: Expression<String>)

  fun setIconSize(expression: Expression<Number>)

  fun setIconTextFit(expression: Expression<String>)

  fun setIconTextFitPadding(expression: Expression<Point>)

  fun setIconImage(expression: Expression<TResolvedImage>)

  fun setIconRotate(expression: Expression<Number>)

  fun setIconPadding(expression: Expression<Number>)

  fun setIconKeepUpright(expression: Expression<Boolean>)

  fun setIconOffset(expression: Expression<Point>)

  fun setIconAnchor(expression: Expression<String>)

  fun setIconPitchAlignment(expression: Expression<String>)

  fun setIconOpacity(expression: Expression<Number>)

  fun setIconColor(expression: Expression<Color>)

  fun setIconHaloColor(expression: Expression<Color>)

  fun setIconHaloWidth(expression: Expression<Number>)

  fun setIconHaloBlur(expression: Expression<Number>)

  fun setIconTranslate(expression: Expression<Point>)

  fun setIconTranslateAnchor(expression: Expression<String>)

  fun setTextPitchAlignment(expression: Expression<String>)

  fun setTextRotationAlignment(expression: Expression<String>)

  fun setTextField(expression: Expression<String>)

  fun setTextFont(expression: Expression<List<String>>)

  fun setTextSize(expression: Expression<Number>)

  fun setTextMaxWidth(expression: Expression<Number>)

  fun setTextLineHeight(expression: Expression<Number>)

  fun setTextLetterSpacing(expression: Expression<Number>)

  fun setTextJustify(expression: Expression<String>)

  fun setTextRadialOffset(expression: Expression<Number>)

  fun setTextVariableAnchor(expression: Expression<List<String>>)

  fun setTextVariableAnchorOffset(expression: Expression<List<*>>)

  fun setTextAnchor(expression: Expression<String>)

  fun setTextMaxAngle(expression: Expression<Number>)

  fun setTextWritingMode(expression: Expression<List<String>>)

  fun setTextRotate(expression: Expression<Number>)

  fun setTextPadding(expression: Expression<Number>)

  fun setTextKeepUpright(expression: Expression<Boolean>)

  fun setTextTransform(expression: Expression<String>)

  fun setTextOffset(expression: Expression<Point>)

  fun setTextAllowOverlap(expression: Expression<Boolean>)

  fun setTextOverlap(expression: Expression<String>)

  fun setTextIgnorePlacement(expression: Expression<Boolean>)

  fun setTextOptional(expression: Expression<Boolean>)

  fun setTextOpacity(expression: Expression<Number>)

  fun setTextColor(expression: Expression<Color>)

  fun setTextHaloColor(expression: Expression<Color>)

  fun setTextHaloWidth(expression: Expression<Number>)

  fun setTextHaloBlur(expression: Expression<Number>)

  fun setTextTranslate(expression: Expression<Point>)

  fun setTextTranslateAnchor(expression: Expression<String>)
}
