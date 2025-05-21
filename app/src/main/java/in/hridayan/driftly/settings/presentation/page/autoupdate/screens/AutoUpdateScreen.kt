@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.autoupdate.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel.AutoUpdateViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun AutoUpdateScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    autoUpdateViewModel: AutoUpdateViewModel = hiltViewModel()
) {
    var checked = LocalSettings.current.isAutoUpdate
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        autoUpdateViewModel.updateEvents.collect { result ->
            when (result) {
                is UpdateResult.Success -> {
                    if (result.isUpdateAvailable) {
                        Toast.makeText(context, "Update available", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "No updates", Toast.LENGTH_SHORT).show()
                    }
                }

                UpdateResult.NetworkError -> {
                    Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
                }

                UpdateResult.Timeout -> {
                    Toast.makeText(context, "Request timeout", Toast.LENGTH_SHORT).show()
                }

                UpdateResult.UnknownError -> {
                    Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.auto_update)
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 25.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .clickable(enabled = true, onClick = { checked = !checked }),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.enable_auto_update),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Switch(checked = checked, onCheckedChange = {
                            settingsViewModel.onToggle(
                                SettingsKeys.AUTO_UPDATE
                            )
                        })
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.update_channel),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(25.dp)
                )
            }

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(end = 25.dp)
                            .align(Alignment.CenterEnd),
                        onClick = {
                            autoUpdateViewModel.checkForUpdates(BuildConfig.VERSION_NAME)
                        }) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_update),
                                contentDescription = null,
                            )
                            Text(
                                text = stringResource(R.string.check_for_updates),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}