@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.home.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.SubjectError
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel

@Composable
fun EditSubjectDialog(
    modifier: Modifier = Modifier,
    subjectId: Int,
    subject: String,
    viewModel: HomeViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.setSubjectNamePlaceholder(subject)
    }

    val weakHaptic = LocalWeakHaptic.current
    val subject by viewModel.subject.collectAsState()
    val subjectError by viewModel.subjectError.collectAsState()

    val interactionSources = remember { List(2) { MutableInteractionSource() } }

    Dialog(
        onDismissRequest = {
            viewModel.resetInputFields()
            onDismiss()
        },
        content = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(Shape.cardCornerLarge)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(25.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val label = when (subjectError) {
                    SubjectError.Empty -> stringResource(R.string.field_blank_error)
                    SubjectError.AlreadyExists -> stringResource(R.string.subject_already_exists)
                    is SubjectError.Unknown -> stringResource(R.string.unknown_error)
                    else -> stringResource(R.string.enter_new_name)
                }

                AutoResizeableText(
                    text = stringResource(R.string.update_subject),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                )

                OutlinedTextField(
                    value = subject,
                    onValueChange = {
                        viewModel.onSubjectChange(it)
                    },
                    isError = subjectError != SubjectError.None,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = label) }
                )

                @Suppress("DEPRECATION")
                ButtonGroup(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = {
                            weakHaptic()
                            viewModel.resetInputFields()
                            onDismiss()
                        },
                        shapes = ButtonDefaults.shapes(),
                        modifier = Modifier
                            .weight(1f)
                            .animateWidth(interactionSources[0]),
                        interactionSource = interactionSources[0],
                        content = {
                            AutoResizeableText(
                                text = stringResource(R.string.cancel),
                            )
                        }
                    )

                    Button(
                        onClick = {
                            weakHaptic()
                            viewModel.updateSubject(
                                subjectId = subjectId,
                                onSuccess = {
                                    viewModel.resetInputFields()
                                    onDismiss()
                                }
                            )
                        },
                        shapes = ButtonDefaults.shapes(),
                        modifier = Modifier
                            .weight(1f)
                            .animateWidth(interactionSources[1]),
                        interactionSource = interactionSources[1],
                        content = {
                            AutoResizeableText(
                                text = stringResource(R.string.update),
                            )
                        }
                    )
                }
            }
        }
    )
}
