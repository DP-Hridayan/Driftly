package `in`.hridayan.driftly.settings.presentation.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.settings.data.local.model.PreferenceItem
import `in`.hridayan.driftly.settings.domain.model.getResolvedDescription
import `in`.hridayan.driftly.settings.domain.model.getResolvedIcon
import `in`.hridayan.driftly.settings.domain.model.getResolvedTitle
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun NullPreferenceItemView(
    modifier: Modifier = Modifier,
    item: PreferenceItem,
    contentDescription: String = "",
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    if (!item.isLayoutVisible) return

    val weakHaptic = LocalWeakHaptic.current

    val icon = item.getResolvedIcon()
    val titleText = item.getResolvedTitle()
    val descriptionText = item.getResolvedDescription()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true, onClick = {
                    weakHaptic()
                    settingsViewModel.onItemClicked(item.key)
                })
            .padding(horizontal = 20.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            if (titleText.isNotEmpty()) {
                Text(
                    text = titleText,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.alpha(0.95f)
                )
            }

            if (descriptionText.isNotEmpty()) {
                Text(
                    text = descriptionText,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.90f)
                )
            }
        }
    }
}