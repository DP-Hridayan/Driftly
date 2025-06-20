@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.settings.presentation.page.autoupdate.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.presentation.components.bottomsheet.UpdateBottomSheet
import `in`.hridayan.driftly.core.presentation.components.progress.LoadingSpinner
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.presentation.components.dialog.LatestVersionDialog
import `in`.hridayan.driftly.settings.presentation.components.item.PreferenceItemView
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
    val context = LocalContext.current
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var showUpdateSheet by rememberSaveable { mutableStateOf(false) }
    var tagName by rememberSaveable { mutableStateOf(BuildConfig.VERSION_NAME) }
    var apkUrl by rememberSaveable { mutableStateOf("") }
    val includePrerelease = LocalSettings.current.githubReleaseType == GithubReleaseType.PRE_RELEASE
    val networkError = stringResource(R.string.network_error)
    val requestTimeout = stringResource(R.string.request_timeout)
    val unKnownError = stringResource(R.string.unknown_error)
    val settings = settingsViewModel.autoUpdatePageList
    var showLatestVersionDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        autoUpdateViewModel.updateEvents.collect { result ->
            showLoading = false
            when (result) {
                is UpdateResult.Success -> {
                    if (result.isUpdateAvailable) {
                        tagName = result.release.tagName
                        apkUrl = result.release.apkUrl.toString()
                        showUpdateSheet = true
                    } else {
                        showLatestVersionDialog = true
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
            contentPadding = innerPadding
        ) {
            itemsIndexed(settings) { index, group ->
                when (group) {
                    is PreferenceGroup.Category -> {
                        Text(
                            text = stringResource(group.categoryNameResId),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .animateItem()
                                .padding(horizontal = 20.dp, vertical = 25.dp)
                        )
                        group.items.forEach { item ->
                            PreferenceItemView(item = item, modifier = modifier.animateItem())
                        }
                    }

                    is PreferenceGroup.Items -> {
                        group.items.forEach { item ->
                            PreferenceItemView(item = item, modifier = modifier.animateItem())
                        }
                    }

                    is PreferenceGroup.CustomComposable -> {
                        if (group.label == "check_update_button") {
                            CheckUpdateButton(
                                showLoading = showLoading,
                                onClick = {
                                    weakHaptic()
                                    autoUpdateViewModel.checkForUpdates(
                                        includePrerelease = includePrerelease
                                    )
                                    showLoading = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    is PreferenceGroup.HorizontalDivider -> {
                        HorizontalDivider(
                            modifier = modifier.fillMaxWidth(),
                            thickness = 1.dp
                        )
                    }
                }
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

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
            }
        }
    }

    if (showUpdateSheet) {
        UpdateBottomSheet(
            onDismiss = { showUpdateSheet = false },
            latestVersion = tagName,
            apkUrl = apkUrl
        )
    }

    if (showLatestVersionDialog) {
        LatestVersionDialog(onDismiss = { showLatestVersionDialog = false })
    }
}

@Composable
private fun CheckUpdateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showLoading: Boolean
) {
    Box(modifier = modifier) {
        Button(
            modifier = Modifier
                .padding(end = 25.dp, bottom = 25.dp, top = 15.dp)
                .align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shapes = ButtonDefaults.shapes(),
            onClick = onClick
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showLoading)
                    LoadingSpinner(modifier = Modifier.size(20.dp))
                else
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Update,
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