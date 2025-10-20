@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.core.presentation.components.dialog

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.checkbox.CheckboxWithText
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun GithubDownloadWarningDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    val interactionSources = remember { List(2) { MutableInteractionSource() } }
    var checkedState by rememberSaveable { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surfaceContainer
        ) {
            Column(
                modifier = modifier
                    .padding(24.dp)
                    .widthIn(min = 280.dp)
            ) {
                AutoResizeableText(
                    text = stringResource(R.string.warning),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.warning_github_download),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                CheckboxWithText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    checkedState = checkedState,
                    onCheckChanged = {
                        checkedState = !checkedState
                    },
                    text = stringResource(R.string.donot_show_again)
                )
                Spacer(modifier = Modifier.height(16.dp))

                @Suppress("DEPRECATION")
                ButtonGroup(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            weakHaptic()
                        },
                        shapes = ButtonDefaults.shapes(),
                        modifier = Modifier
                            .weight(1f)
                            .animateWidth(interactionSources[0]),
                        interactionSource = interactionSources[0],
                    ) {
                        AutoResizeableText(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        onClick = {
                            onConfirm()
                            settingsViewModel.setBoolean(
                                key = SettingsKeys.SHOW_GITHUB_WARNING_DIALOG,
                                value = !checkedState
                            )
                            weakHaptic()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .animateWidth(interactionSources[1]),
                        interactionSource = interactionSources[1],
                        shapes = ButtonDefaults.shapes(),
                    ) {
                        AutoResizeableText(
                            text = stringResource(R.string.download),
                        )
                    }
                }
            }
        }
    }
}