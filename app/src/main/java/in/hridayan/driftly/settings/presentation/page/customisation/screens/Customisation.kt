@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.customisation.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.home.presentation.components.card.SubjectCard
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.page.customisation.viewmodel.CustomisationViewModel

@Composable
fun CustomisationScreen(
    modifier: Modifier = Modifier,
    customisationViewModel: CustomisationViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    var cardCornerSliderValue =
        customisationViewModel.subjectCardCornerRadius.collectAsState(initial = 8f)

    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.customisation)
    ) { innerPadding, topBarScrollBehavior ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ) {
            item {
                Text(
                    text = stringResource(R.string.subject_card),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                )
            }

            item {
                SubjectCard(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    subject = stringResource(R.string.subject_name),
                    subjectId = 999,
                    progress = 0.67f,
                    isDemoCard = true,
                    cornerRadius = cardCornerSliderValue.value.dp
                )
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
        }
    }
}