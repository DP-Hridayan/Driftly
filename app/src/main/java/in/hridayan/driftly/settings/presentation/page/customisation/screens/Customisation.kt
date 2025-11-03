@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.customisation.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.SubjectCardStyle
import `in`.hridayan.driftly.home.presentation.components.card.SubjectCard
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.page.customisation.viewmodel.CustomisationViewModel

@Composable
fun CustomisationScreen(
    modifier: Modifier = Modifier,
    customisationViewModel: CustomisationViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    val cardCornerSliderValue =
        customisationViewModel.subjectCardCornerRadius.collectAsState(initial = 8f)
    val currentCardStyle by customisationViewModel.cardStyle.collectAsState()
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val cardStyleList = listOf(SubjectCardStyle.CARD_STYLE_A, SubjectCardStyle.CARD_STYLE_B)
    val listState = rememberLazyListState()

    SettingsScaffold(
        modifier = modifier,
        listState = listState,
        topBarTitle = stringResource(R.string.customisation),
        content = { innerPadding, topBarScrollBehavior ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
                state = listState,
                contentPadding = innerPadding
            ) {
                item {
                    Text(
                        text = stringResource(R.string.subject_card_style),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                    )
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        cardStyleList.forEach { style ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, bottom = 8.dp, start = 10.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (style == currentCardStyle),
                                    onClick = {
                                        customisationViewModel.select(style)
                                        weakHaptic()
                                    }
                                )

                                SubjectCard(
                                    subject = stringResource(R.string.subject_name),
                                    subjectId = 999,
                                    progress = 0.67f,
                                    isDemoCard = true,
                                    cornerRadius = cardCornerSliderValue.value.dp,
                                    cardStyle = style,
                                    onClick = { customisationViewModel.select(style) }
                                )
                            }
                        }

                    }

                }

                item {
                    Text(
                        text = stringResource(R.string.adjust_card_corner_radius),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                    )
                }

                item {
                    Slider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        value = cardCornerSliderValue.value,
                        onValueChange = {
                            customisationViewModel.setSubjectCardCornerRadius(it)
                            weakHaptic()
                        },
                        valueRange = 0f..36f,
                        steps = 36,
                        thumb = {
                            SliderDefaults.Thumb(interactionSource = interactionSource)
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                    )
                }
            }
        },
    )
}