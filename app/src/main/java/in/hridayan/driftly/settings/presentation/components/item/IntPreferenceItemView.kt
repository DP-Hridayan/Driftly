package `in`.hridayan.driftly.settings.presentation.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.settings.data.local.model.PreferenceItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun IntPreferenceItemView(
    modifier: Modifier = Modifier,
    item: PreferenceItem,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    if (!item.isLayoutVisible) return

    val weakHaptic = LocalWeakHaptic.current

    val selected =
        settingsViewModel.getInt(key = item.key).collectAsState(initial = item.key.default as Int)

    val onSelectedChange: (Int) -> Unit = {
        settingsViewModel.setInt(key = item.key, value = it)
    }

    val intItems = item as PreferenceItem.IntPreferenceItem

    if (item.type == SettingsType.RadioGroup) {
        Column(modifier = modifier) {
            intItems.radioOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectedChange(option.value)
                            weakHaptic()
                        }
                        .padding(vertical = 8.dp, horizontal = 20.dp)
                ) {
                    Text(
                        text = stringResource(option.labelResId),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    RadioButton(
                        selected = (option.value == selected.value),
                        onClick = {
                            onSelectedChange(option.value)
                            weakHaptic()
                        }
                    )
                }
            }
        }
    }
}