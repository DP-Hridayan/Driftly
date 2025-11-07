@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.settings.presentation.components.bottomsheet

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.card.PillShapedCard
import `in`.hridayan.driftly.core.presentation.components.card.RoundedCornerCard
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import `in`.hridayan.driftly.core.presentation.provider.getFontFamily
import `in`.hridayan.driftly.core.presentation.provider.oneUiSans
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.CustomFontFamily
import `in`.hridayan.driftly.settings.presentation.components.shape.CardCornerShape.getRoundedShape
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel.LookAndFeelViewModel
import `in`.hridayan.driftly.settings.presentation.provider.RadioGroupOptionsProvider
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun FontStyleBottomSheet(
    onDismiss: () -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    viewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()
    val interactionSources = remember { List(2) { MutableInteractionSource() } }
    val fontStyles = RadioGroupOptionsProvider.fontStyleOptions
    val selected =
        settingsViewModel.getInt(key = SettingsKeys.FONT_FAMILY)
            .collectAsState(initial = SettingsKeys.FONT_FAMILY.default as Int)
    var tempSelected by remember { mutableIntStateOf(selected.value) }

    val isCheckedMatchCase by viewModel.isCheckedMatchCase.collectAsState()
    val isCheckedBold by viewModel.isCheckedBold.collectAsState()
    val isCheckedItalic by viewModel.isCheckedItalic.collectAsState()
    val isCheckedUnderline by viewModel.isCheckedUnderline.collectAsState()

    val displayFont = getFontFamily(tempSelected)
    val previewText = stringResource(R.string.font_display_text)
    val displayText = when {
        isCheckedMatchCase -> previewText.uppercase()
        else -> previewText.lowercase()
    }

    LaunchedEffect(selected.value) {
        tempSelected = selected.value
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
        ) {
            AutoResizeableText(
                stringResource(R.string.font_family),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AutoResizeableText(
                stringResource(R.string.preview),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .clip(MaterialTheme.shapes.largeIncreased)
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.largeIncreased,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = displayFont,
                        fontWeight = if (isCheckedBold) FontWeight.Bold else FontWeight.Normal,
                        fontStyle = if (isCheckedItalic) FontStyle.Italic else FontStyle.Normal,
                        textDecoration = if (isCheckedUnderline) TextDecoration.Underline else TextDecoration.None
                    )
                )
            }

            TextFormatUtilityRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(scrollState)
                    .padding(bottom = 24.dp)
            ) {
                fontStyles.forEachIndexed { index, option ->
                    val shape = getRoundedShape(index, fontStyles.size)

                    val selected = option.value == tempSelected

                    val cardColors = if (selected) CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) else CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )

                    val animatedCorner by animateDpAsState(
                        targetValue = if (selected) 32.dp else 4.dp,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        ),
                        label = "cornerAnimation"
                    )

                    val finalShape = if (selected) {
                        RoundedCornerShape(animatedCorner)
                    } else {
                        shape
                    }

                    val labelNameFontFamily = when (option.value) {
                        CustomFontFamily.ONE_UI_SANS -> oneUiSans
                        CustomFontFamily.MONOSPACE -> FontFamily.Monospace
                        CustomFontFamily.SANS_SERIF -> FontFamily.SansSerif
                        else -> FontFamily.Default
                    }

                    RoundedCornerCard(
                        modifier = Modifier.fillMaxWidth(),
                        roundedCornerShape = finalShape,
                        paddingValues = PaddingValues(vertical = 1.dp),
                        colors = cardColors
                    )
                    {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    tempSelected = option.value
                                    weakHaptic()
                                }
                                .padding(vertical = 8.dp, horizontal = 20.dp)
                        ) {
                            Text(
                                text = stringResource(option.labelResId),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                fontFamily = labelNameFontFamily
                            )

                            Spacer(Modifier.weight(1f))

                            RadioButton(
                                selected = (option.value == tempSelected),
                                onClick = {
                                    tempSelected = option.value
                                    weakHaptic()
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    unselectedColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            @Suppress("DEPRECATION")
            ButtonGroup(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .animateWidth(interactionSources[0]),
                    shapes = ButtonDefaults.shapes(),
                    interactionSource = interactionSources[0],
                    onClick = {
                        weakHaptic()
                        onDismiss()
                    },
                    content = { Text(text = stringResource(R.string.cancel)) }
                )

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .animateWidth(interactionSources[1]),
                    shapes = ButtonDefaults.shapes(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    interactionSource = interactionSources[1],
                    onClick = {
                        weakHaptic()
                        settingsViewModel.setInt(
                            key = SettingsKeys.FONT_FAMILY,
                            value = tempSelected
                        )
                        onDismiss()
                    },
                    content = { Text(text = stringResource(R.string.confirm)) }
                )
            }
        }
    }
}

@Composable
fun TextFormatUtilityRow(
    modifier: Modifier = Modifier,
    viewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    val isCheckedMatchCase by viewModel.isCheckedMatchCase.collectAsState()
    val isCheckedBold by viewModel.isCheckedBold.collectAsState()
    val isCheckedItalic by viewModel.isCheckedItalic.collectAsState()
    val isCheckedUnderline by viewModel.isCheckedUnderline.collectAsState()

    val checkedContainerColor = MaterialTheme.colorScheme.primaryContainer
    val checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val unCheckedContainerColor = BottomSheetDefaults.ContainerColor
    val unCheckedContentColor = MaterialTheme.colorScheme.onSurface

    PillShapedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = BottomSheetDefaults.ContainerColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(if (isCheckedMatchCase) checkedContainerColor else unCheckedContainerColor)
                    .clickable(
                        enabled = true,
                        onClick = {
                            weakHaptic()
                            viewModel.toggleMatchCase()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_matchcase),
                    contentDescription = null,
                    tint = if (isCheckedMatchCase) checkedContentColor else unCheckedContentColor,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(if (isCheckedBold) checkedContainerColor else unCheckedContainerColor)
                    .clickable(
                        enabled = true,
                        onClick = {
                            weakHaptic()
                            viewModel.toggleBold()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_bold),
                    contentDescription = null,
                    tint = if (isCheckedBold) checkedContentColor else unCheckedContentColor,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(if (isCheckedItalic) checkedContainerColor else unCheckedContainerColor)
                    .clickable(
                        enabled = true,
                        onClick = {
                            weakHaptic()
                            viewModel.toggleItalic()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_italic),
                    contentDescription = null,
                    tint = if (isCheckedItalic) checkedContentColor else unCheckedContentColor,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(if (isCheckedUnderline) checkedContainerColor else unCheckedContainerColor)
                    .clickable(
                        enabled = true,
                        onClick = {
                            weakHaptic()
                            viewModel.toggleUnderline()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_underline),
                    contentDescription = null,
                    tint = if (isCheckedUnderline) checkedContentColor else unCheckedContentColor,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight())

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(
                        enabled = true,
                        onClick = {
                            weakHaptic()
                            viewModel.formatClear()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_format_clear),
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }
}