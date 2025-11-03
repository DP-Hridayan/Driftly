@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.notification.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.notification.isNotificationPermissionGranted
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.PreferenceGroup
import `in`.hridayan.driftly.settings.presentation.components.item.PreferenceItemView
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.components.shape.CardCornerShape.getRoundedShape
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val settings = settingsViewModel.notificationsPageList

    val notificationPreference = LocalSettings.current.notificationPreference

    val launcherReqPerm = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            settingsViewModel.setBoolean(
                key = SettingsKeys.ENABLE_NOTIFICATIONS,
                value = isGranted || notificationPreference
            )

            settingsViewModel.refreshNotificationPermissionState()
        }
    )

    val launcherIntent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            val isGranted = isNotificationPermissionGranted(context)

            settingsViewModel.setBoolean(
                key = SettingsKeys.ENABLE_NOTIFICATIONS,
                value = isGranted || notificationPreference
            )

            settingsViewModel.refreshNotificationPermissionState()
        }
    )

    LaunchedEffect(Unit) {
        settingsViewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.RequestPermission -> launcherReqPerm.launch(event.permission)

                is SettingsUiEvent.LaunchIntent -> launcherIntent.launch(event.intent)

                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            settingsViewModel.refreshNotificationPermissionState()
        }
    }

    val listState = rememberLazyListState()

    SettingsScaffold(
        modifier = modifier,
        listState = listState,
        topBarTitle = stringResource(R.string.notifications),
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