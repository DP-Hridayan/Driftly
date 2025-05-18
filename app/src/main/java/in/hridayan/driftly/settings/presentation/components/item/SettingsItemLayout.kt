package `in`.hridayan.driftly.settings.presentation.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SettingsItemLayout(
    modifier: Modifier = Modifier,
    item: SettingsItem,
    isEnabled: Flow<Boolean> = flowOf(false),
    isLayoutVisible: Boolean = true,
    onToggle: () -> Unit,
    onClick: (SettingsItem) -> Unit = {},
    contentDescription: String = "",
) {
    val weakHaptic = LocalWeakHaptic.current
    val checked by isEnabled.collectAsState(initial = false)
    if (isLayoutVisible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true, onClick = {
                        onClick(item)
                        onToggle()
                        weakHaptic()
                    })
                .padding(horizontal = 15.dp, vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Icon(
                painter = painterResource(item.icon),
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                val titleText = when {
                    item.titleResId != 0 -> runCatching { stringResource(item.titleResId) }.getOrElse { "" }
                    item.titleString.isNotBlank() -> item.titleString
                    else -> null
                }

                if (!titleText.isNullOrEmpty()) {
                    Text(
                        text = titleText,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.alpha(0.95f)
                    )
                }

                val descriptionText = when {
                    item.descriptionResId != 0 -> runCatching { stringResource(item.descriptionResId) }.getOrElse { "" }
                    item.descriptionString.isNotBlank() -> item.descriptionString
                    else -> null
                }

                if (!descriptionText.isNullOrEmpty()) {
                    Text(
                        text = descriptionText,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.alpha(0.90f)
                    )
                }
            }

            if (item.type == SettingsType.Switch) {
                Switch(checked = checked, onCheckedChange = {
                    onToggle()
                    weakHaptic()
                })
            }
        }
    }
}