@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.autoupdate.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.presentation.components.bottomsheet.UpdateBottomSheet
import `in`.hridayan.driftly.core.presentation.components.progress.LoadingSpinner
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
    val weakHaptic = LocalWeakHaptic.current
    var checked = LocalSettings.current.isAutoUpdate
    val context = LocalContext.current
    var showLoading by rememberSaveable { mutableStateOf(false) }
    val releaseTypes = listOf<Int>(GithubReleaseType.STABLE, GithubReleaseType.PRE_RELEASE)
    val currentReleaseType by autoUpdateViewModel.githubReleaseType.collectAsState()
    var showUpdateSheet by rememberSaveable { mutableStateOf(false) }
    var tagName by rememberSaveable { mutableStateOf(BuildConfig.VERSION_NAME) }
    val includePrerelease = LocalSettings.current.githubReleaseType == GithubReleaseType.PRE_RELEASE
    val noUpdateAvailable = stringResource(R.string.no_update_available)
    val networkError = stringResource(R.string.network_error)
    val requestTimeout = stringResource(R.string.request_timeout)
    val unKnownError = stringResource(R.string.unknown_error)

    LaunchedEffect(Unit) {
        autoUpdateViewModel.updateEvents.collect { result ->
            showLoading = false
            when (result) {
                is UpdateResult.Success -> {
                    if (result.isUpdateAvailable) {
                        tagName = result.release.tagName
                        showUpdateSheet = true
                    } else {
                        Toast.makeText(context, noUpdateAvailable, Toast.LENGTH_SHORT).show()
                    }
                }

                UpdateResult.NetworkError -> {
                    Toast.makeText(context, networkError, Toast.LENGTH_SHORT).show()
                }

                UpdateResult.Timeout -> {
                    Toast.makeText(context, requestTimeout, Toast.LENGTH_SHORT).show()
                }

                UpdateResult.UnknownError -> {
                    Toast.makeText(context, unKnownError, Toast.LENGTH_SHORT).show()
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
                        .clickable(enabled = true, onClick = {
                            weakHaptic()
                            checked = !checked
                            settingsViewModel.onToggle(
                                SettingsKeys.AUTO_UPDATE
                            )
                        }),
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
                            weakHaptic()
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    releaseTypes.forEach { type ->
                        val label = when (type) {
                            GithubReleaseType.STABLE -> stringResource(R.string.stable)
                            GithubReleaseType.PRE_RELEASE -> stringResource(R.string.pre_release)
                            else -> ""
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    autoUpdateViewModel.select(type)
                                    weakHaptic()
                                }
                                .padding(top = 8.dp, bottom = 8.dp, start = 25.dp, end = 20.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.weight(1f))

                            RadioButton(
                                selected = (currentReleaseType == type),
                                onClick = {
                                    autoUpdateViewModel.select(type)
                                    weakHaptic()
                                }
                            )
                        }
                    }
                }
            }

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .padding(end = 25.dp, bottom = 25.dp)
                            .align(Alignment.CenterEnd),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        onClick = {
                            weakHaptic()
                            autoUpdateViewModel.checkForUpdates(
                                currentVersion = BuildConfig.VERSION_NAME,
                                includePrerelease = includePrerelease
                            )
                            showLoading = true
                        }) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (showLoading)
                                LoadingSpinner(modifier = Modifier.size(20.dp))
                            else
                                Icon(
                                    modifier = Modifier.size(20.dp),
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

            item {
                HorizontalDivider(
                    modifier = modifier.fillMaxWidth(),
                    thickness = 1.dp
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = stringResource(R.string.pre_release_warning),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Text(
                        text = stringResource(R.string.pre_release_warning_description),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

    if (showUpdateSheet) {
        UpdateBottomSheet(
            onDismiss = { showUpdateSheet = false },
            latestVersion = tagName
        )
    }
}