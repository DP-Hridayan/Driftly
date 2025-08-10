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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import `in`.hridayan.driftly.settings.presentation.components.shape.getRoundedShape
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

    val listState = rememberLazyListState()

    SettingsScaffold(
        modifier = modifier,
        listState = listState,
        topBarTitle = stringResource(R.string.auto_update),
        content = { innerPadding, topBarScrollBehavior ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
                state = listState,
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
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 30.dp,
                                        bottom = 10.dp
                                    )
                            )
                            val visibleItems = group.items.filter { it.isLayoutVisible }

                            visibleItems.forEachIndexed { i, item ->
                                val shape = getRoundedShape(i, visibleItems.size)

                                PreferenceItemView(
                                    item = item,
                                    modifier = Modifier.animateItem(),
                                    roundedShape = shape
                                )
                            }
                        }

                        is PreferenceGroup.Items -> {
                            val visibleItems = group.items.filter { it.isLayoutVisible }

                            visibleItems.forEachIndexed { i, item ->
                                val shape = getRoundedShape(i, visibleItems.size)

                                PreferenceItemView(
                                    item = item,
                                    modifier = Modifier.animateItem(),
                                    roundedShape = shape
                                )
                            }
                        }

                        else -> {}
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, end = 25.dp, top = 25.dp, bottom = 75.dp),
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
                                tint = MaterialTheme.colorScheme.error,
                            )

                            Text(
                                text = stringResource(R.string.pre_release_warning),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        Text(
                            text = stringResource(R.string.pre_release_warning_description),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
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
        }, fabContent = { expanded ->
            CheckUpdateButton(
                showLoading = showLoading,
                expanded = expanded,
                onClick = {
                    weakHaptic()
                    autoUpdateViewModel.checkForUpdates(
                        includePrerelease = includePrerelease
                    )
                    showLoading = true
                })
        })

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
    expanded: Boolean = true,
    showLoading: Boolean
) {
    ExtendedFloatingActionButton(
        modifier = modifier.padding(bottom = 10.dp),
        onClick = onClick,
        expanded = expanded,
        icon = {
            if (showLoading)
                LoadingSpinner(modifier = Modifier.size(24.dp))
            else
                Icon(
                    imageVector = Icons.Rounded.Update,
                    contentDescription = null,
                )
        },
        text = {
            Text(
                text = stringResource(R.string.check_for_updates),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    )
}