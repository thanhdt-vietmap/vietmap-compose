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

internal expect class SymbolLayer(id: String, source: Source) : FeatureLayer {
  override var sourceLayer: String

  override fun setFilter(filter: CompiledExpression<BooleanValue>)

  fun setSymbolPlacement(placement: CompiledExpression<SymbolPlacement>)

  fun setSymbolSpacing(spacing: CompiledExpression<DpValue>)

  fun setSymbolAvoidEdges(avoidEdges: CompiledExpression<BooleanValue>)

  fun setSymbolSortKey(sortKey: CompiledExpression<FloatValue>)

  fun setSymbolZOrder(zOrder: CompiledExpression<SymbolZOrder>)

  fun setIconAllowOverlap(allowOverlap: CompiledExpression<BooleanValue>)

  fun setIconOverlap(overlap: CompiledExpression<StringValue>)

  fun setIconIgnorePlacement(ignorePlacement: CompiledExpression<BooleanValue>)

  fun setIconOptional(optional: CompiledExpression<BooleanValue>)

  fun setIconRotationAlignment(rotationAlignment: CompiledExpression<IconRotationAlignment>)

  fun setIconSize(size: CompiledExpression<FloatValue>)

  fun setIconTextFit(textFit: CompiledExpression<IconTextFit>)

  fun setIconTextFitPadding(textFitPadding: CompiledExpression<DpPaddingValue>)

  fun setIconImage(image: CompiledExpression<ImageValue>)

  fun setIconRotate(rotate: CompiledExpression<FloatValue>)

  fun setIconPadding(padding: CompiledExpression<DpValue>)

  fun setIconKeepUpright(keepUpright: CompiledExpression<BooleanValue>)

  fun setIconOffset(offset: CompiledExpression<DpOffsetValue>)

  fun setIconAnchor(anchor: CompiledExpression<SymbolAnchor>)

  fun setIconPitchAlignment(pitchAlignment: CompiledExpression<IconPitchAlignment>)

  fun setIconOpacity(opacity: CompiledExpression<FloatValue>)

  fun setIconColor(color: CompiledExpression<ColorValue>)

  fun setIconHaloColor(haloColor: CompiledExpression<ColorValue>)

  fun setIconHaloWidth(haloWidth: CompiledExpression<DpValue>)

  fun setIconHaloBlur(haloBlur: CompiledExpression<DpValue>)

  fun setIconTranslate(translate: CompiledExpression<DpOffsetValue>)

  fun setIconTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>)

  fun setTextPitchAlignment(pitchAlignment: CompiledExpression<TextPitchAlignment>)

  fun setTextRotationAlignment(rotationAlignment: CompiledExpression<TextRotationAlignment>)

  fun setTextField(field: CompiledExpression<FormattedValue>)

  fun setTextFont(font: CompiledExpression<ListValue<StringValue>>)

  fun setTextSize(size: CompiledExpression<DpValue>)

  fun setTextMaxWidth(maxWidth: CompiledExpression<FloatValue>)

  fun setTextLineHeight(lineHeight: CompiledExpression<FloatValue>)

  fun setTextLetterSpacing(letterSpacing: CompiledExpression<FloatValue>)

  fun setTextJustify(justify: CompiledExpression<TextJustify>)

  fun setTextRadialOffset(radialOffset: CompiledExpression<FloatValue>)

  fun setTextVariableAnchor(variableAnchor: CompiledExpression<ListValue<SymbolAnchor>>)

  fun setTextVariableAnchorOffset(
    variableAnchorOffset: CompiledExpression<TextVariableAnchorOffsetValue>
  )

  fun setTextAnchor(anchor: CompiledExpression<SymbolAnchor>)

  fun setTextMaxAngle(maxAngle: CompiledExpression<FloatValue>)

  fun setTextWritingMode(writingMode: CompiledExpression<ListValue<TextWritingMode>>)

  fun setTextRotate(rotate: CompiledExpression<FloatValue>)

  fun setTextPadding(padding: CompiledExpression<DpValue>)

  fun setTextKeepUpright(keepUpright: CompiledExpression<BooleanValue>)

  fun setTextTransform(transform: CompiledExpression<TextTransform>)

  fun setTextOffset(offset: CompiledExpression<FloatOffsetValue>)

  fun setTextAllowOverlap(allowOverlap: CompiledExpression<BooleanValue>)

  fun setTextOverlap(overlap: CompiledExpression<SymbolOverlap>)

  fun setTextIgnorePlacement(ignorePlacement: CompiledExpression<BooleanValue>)

  fun setTextOptional(optional: CompiledExpression<BooleanValue>)

  fun setTextOpacity(opacity: CompiledExpression<FloatValue>)

  fun setTextColor(color: CompiledExpression<ColorValue>)

  fun setTextHaloColor(haloColor: CompiledExpression<ColorValue>)

  fun setTextHaloWidth(haloWidth: CompiledExpression<DpValue>)

  fun setTextHaloBlur(haloBlur: CompiledExpression<DpValue>)

  fun setTextTranslate(translate: CompiledExpression<DpOffsetValue>)

  fun setTextTranslateAnchor(translateAnchor: CompiledExpression<TranslateAnchor>)
}
