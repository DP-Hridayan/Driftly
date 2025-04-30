package `in`.hridayan.driftly.home.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.domain.model.SubjectError
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel


@Composable
fun AddSubjectDialog(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val subject by viewModel.subject.collectAsState()
    val subjectError by viewModel.subjectError.collectAsState()

    Dialog(
        onDismissRequest = onDismiss,
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

                val label = when(subjectError){
                    SubjectError.Empty  -> stringResource(R.string.field_blank_error)
                    SubjectError.AlreadyExists -> stringResource(R.string.subject_already_exists)
                    else -> stringResource(R.string.add_subject)
                }
                Text(
                    text = stringResource(R.string.add_subject),
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
                    label = { Text(text = label) },
                )

                Row {
                    OutlinedButton(
                        modifier = Modifier.weight(0.4f),
                        onClick = { onDismiss() },
                        content = { Text(text = stringResource(R.string.cancel)) }
                    )

                    Spacer(modifier = Modifier.weight(0.1f))

                    Button(
                        modifier = Modifier.weight(0.4f),
                        onClick = {
                            viewModel.addSubject(
                                onSuccess = {
                                    onDismiss()
                                }
                            )
                        },
                        content = { Text(text = stringResource(R.string.add)) }
                    )
                }
            }
        }
    )
}
