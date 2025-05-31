@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.backup.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup
import `in`.hridayan.driftly.settings.presentation.components.dialog.ResetSettingsDialog
import `in`.hridayan.driftly.settings.presentation.components.dialog.RestoreBackupDialog
import `in`.hridayan.driftly.settings.presentation.components.item.PreferenceItemView
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.page.backup.viewmodel.BackupAndRestoreViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun BackupAndRestoreScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    backupAndRestoreViewModel: BackupAndRestoreViewModel = hiltViewModel()
) {
    val settings = settingsViewModel.backupPageList
    var showResetDialog by rememberSaveable { mutableStateOf(false) }
    var showRestoreBackupDialog by rememberSaveable { mutableStateOf(false) }
    val backupTime by backupAndRestoreViewModel.backupTime.collectAsState()
    val lastBackupTime by settingsViewModel.getString(SettingsKeys.LAST_BACKUP_TIME)
        .collectAsState(initial = "")

    val context = LocalContext.current

    var restoreFileUri by rememberSaveable { mutableStateOf("".toUri()) }

    val launcherBackup = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri ->
        uri?.let { backupAndRestoreViewModel.performBackup(it) }
    }

    val launcherRestore = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            if (it.toString().endsWith(".driftly")) {
                restoreFileUri = it
                backupAndRestoreViewModel.loadBackupTime(it)
                showRestoreBackupDialog = true
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.pick_driftly_extension),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        settingsViewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.ShowDialog -> {
                    showResetDialog = event.key == SettingsKeys.RESET_APP_SETTINGS
                }

                is SettingsUiEvent.RequestDocumentUriForBackup -> {
                    backupAndRestoreViewModel.initiateBackup(event.backupOption)
                    launcherBackup.launch("backup_${System.currentTimeMillis()}.driftly")
                }

                is SettingsUiEvent.RequestDocumentUriForRestore -> {
                    launcherRestore.launch(arrayOf("application/octet-stream"))
                }

                else -> {}
            }
        }
    }

    SettingsScaffold(
        modifier = modifier, topBarTitle = stringResource(R.string.backup_and_restore)
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
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                        )
                        group.items.forEach { item ->
                            PreferenceItemView(item)
                        }
                    }

                    is PreferenceGroup.Items -> {
                        group.items.forEach { item ->
                            PreferenceItemView(item)
                        }
                    }

                    is PreferenceGroup.CustomComposable -> {
                        LastBackupTimeCard(lastBackupTime = lastBackupTime)
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
    }

    if (showResetDialog) {
        ResetSettingsDialog(
            onDismiss = { showResetDialog = false },
            onConfirm = { settingsViewModel.resetSettingsToDefault() })
    }

    if (showRestoreBackupDialog) {
        RestoreBackupDialog(
            onDismiss = { showRestoreBackupDialog = false },
            onConfirm = { backupAndRestoreViewModel.performRestore(restoreFileUri) },
            backupTime = backupTime
        )
    }
}

@Composable
private fun LastBackupTimeCard(modifier: Modifier = Modifier, lastBackupTime: String) {
    val weakHaptic = LocalWeakHaptic.current
    val (date, time) = (lastBackupTime).split(" ").let {
        Pair(
            it.getOrNull(0) ?: stringResource(R.string.none),
            it.getOrNull(1) ?: stringResource(R.string.none)
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 20.dp)
            .clip(
                MaterialTheme.shapes.large
            )
            .clickable(enabled = true, onClick = { weakHaptic() }),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        AutoResizeableText(
            text = stringResource(R.string.last_backup_time) + " : " + time,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp)
        )

        AutoResizeableText(
            text = stringResource(R.string.last_backup_date) + " : " + date,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                start = 25.dp,
                end = 25.dp,
                bottom = 20.dp
            )
        )
    }
}