package dev.sargunv.vietmapcompose.core.layer

import dev.sargunv.vietmapcompose.core.source.Source
import dev.sargunv.vietmapcompose.expressions.ast.CompiledExpression
import dev.sargunv.vietmapcompose.expressions.value.BooleanValue
import dev.sargunv.vietmapcompose.expressions.value.ColorValue
import dev.sargunv.vietmapcompose.expressions.value.DpOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.DpPaddingValue
import dev.sargunv.vietmapcompose.expressions.value.DpValue
import dev.sargunv.vietmapcompose.expressions.value.FloatOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.FloatValue
import dev.sargunv.vietmapcompose.expressions.value.FormattedValue
import dev.sargunv.vietmapcompose.expressions.value.IconPitchAlignment
import dev.sargunv.vietmapcompose.expressions.value.IconRotationAlignment
import dev.sargunv.vietmapcompose.expressions.value.IconTextFit
import dev.sargunv.vietmapcompose.expressions.value.ImageValue
import dev.sargunv.vietmapcompose.expressions.value.ListValue
import dev.sargunv.vietmapcompose.expressions.value.StringValue
import dev.sargunv.vietmapcompose.expressions.value.SymbolAnchor
import dev.sargunv.vietmapcompose.expressions.value.SymbolOverlap
import dev.sargunv.vietmapcompose.expressions.value.SymbolPlacement
import dev.sargunv.vietmapcompose.expressions.value.SymbolZOrder
import dev.sargunv.vietmapcompose.expressions.value.TextJustify
import dev.sargunv.vietmapcompose.expressions.value.TextPitchAlignment
import dev.sargunv.vietmapcompose.expressions.value.TextRotationAlignment
import dev.sargunv.vietmapcompose.expressions.value.TextTransform
import dev.sargunv.vietmapcompose.expressions.value.TextVariableAnchorOffsetValue
import dev.sargunv.vietmapcompose.expressions.value.TextWritingMode
import dev.sargunv.vietmapcompose.expressions.value.TranslateAnchor

internal actual class SymbolLayer actual constructor(id: String, source: Source) :
  FeatureLayer(source) {
  override val impl = TODO()

  actual override var sourceLayer: String = TODO()

  actual override fun setFilter(filter: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setSymbolPlacement(placement: CompiledExpression<SymbolPlacement>) {
    TODO()
  }

  actual fun setSymbolSpacing(spacing: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setSymbolAvoidEdges(avoidEdges: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setSymbolSortKey(sortKey: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setSymbolZOrder(zOrder: CompiledExpression<SymbolZOrder>) {
    TODO()
  }

  actual fun setIconAllowOverlap(allowOverlap: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setIconOverlap(overlap: CompiledExpression<StringValue>) {
    TODO()
  }

  actual fun setIconIgnorePlacement(ignorePlacement: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setIconOptional(optional: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setIconRotationAlignment(
    rotationAlignment: CompiledExpression<IconRotationAlignment>
  ) {
    TODO()
  }

  actual fun setIconSize(size: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setIconTextFit(textFit: CompiledExpression<IconTextFit>) {
    TODO()
  }

  actual fun setIconTextFitPadding(textFitPadding: CompiledExpression<DpPaddingValue>) {
    TODO()
  }

  actual fun setIconImage(image: CompiledExpression<ImageValue>) {
    TODO()
  }

  actual fun setIconRotate(rotate: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setIconPadding(padding: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setIconKeepUpright(keepUpright: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setIconOffset(offset: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setIconAnchor(anchor: CompiledExpression<SymbolAnchor>) {
    TODO()
  }

  actual fun setIconPitchAlignment(pitchAlignment: CompiledExpression<IconPitchAlignment>) {
    TODO()
  }

  actual fun setIconOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setIconColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setIconHaloColor(haloColor: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setIconHaloWidth(haloWidth: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setIconHaloBlur(haloBlur: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setIconTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setIconTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }

  actual fun setTextPitchAlignment(pitchAlignment: CompiledExpression<TextPitchAlignment>) {
    TODO()
  }

  actual fun setTextRotationAlignment(
    rotationAlignment: CompiledExpression<TextRotationAlignment>
  ) {
    TODO()
  }

  actual fun setTextField(field: CompiledExpression<FormattedValue>) {
    TODO()
  }

  actual fun setTextFont(font: CompiledExpression<ListValue<StringValue>>) {
    TODO()
  }

  actual fun setTextSize(size: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setTextMaxWidth(maxWidth: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextLineHeight(lineHeight: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextLetterSpacing(letterSpacing: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextJustify(justify: CompiledExpression<TextJustify>) {
    TODO()
  }

  actual fun setTextRadialOffset(radialOffset: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextVariableAnchor(variableAnchor: CompiledExpression<ListValue<SymbolAnchor>>) {
    TODO()
  }

  actual fun setTextVariableAnchorOffset(
    variableAnchorOffset: CompiledExpression<TextVariableAnchorOffsetValue>
  ) {
    TODO()
  }

  actual fun setTextAnchor(anchor: CompiledExpression<SymbolAnchor>) {
    TODO()
  }

  actual fun setTextMaxAngle(maxAngle: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextWritingMode(writingMode: CompiledExpression<ListValue<TextWritingMode>>) {
    TODO()
  }

  actual fun setTextRotate(rotate: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextPadding(padding: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setTextKeepUpright(keepUpright: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setTextTransform(transform: CompiledExpression<TextTransform>) {
    TODO()
  }

  actual fun setTextOffset(offset: CompiledExpression<FloatOffsetValue>) {
    TODO()
  }

  actual fun setTextAllowOverlap(allowOverlap: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setTextOverlap(overlap: CompiledExpression<SymbolOverlap>) {
    TODO()
  }

  actual fun setTextIgnorePlacement(ignorePlacement: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setTextOptional(optional: CompiledExpression<BooleanValue>) {
    TODO()
  }

  actual fun setTextOpacity(opacity: CompiledExpression<FloatValue>) {
    TODO()
  }

  actual fun setTextColor(color: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setTextHaloColor(haloColor: CompiledExpression<ColorValue>) {
    TODO()
  }

  actual fun setTextHaloWidth(haloWidth: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setTextHaloBlur(haloBlur: CompiledExpression<DpValue>) {
    TODO()
  }

  actual fun setTextTranslate(translate: CompiledExpression<DpOffsetValue>) {
    TODO()
  }

  actual fun setTextTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>) {
    TODO()
  }
}
