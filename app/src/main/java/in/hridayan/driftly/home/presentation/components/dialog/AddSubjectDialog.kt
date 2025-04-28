package `in`.hridayan.driftly.home.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewmodel
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape


@Composable
fun AddSubjectDialog(
    modifier: Modifier = Modifier,
    viewModel: HomeViewmodel = hiltViewModel(),
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
                    .clip(Shape.cardCornerMedium)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                val label =
                    if (subjectError) stringResource(R.string.field_blank_error) else stringResource(
                        R.string.subject
                    )
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
                    isError = subjectError,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = label) }
                )

                Button(
                    modifier = Modifier.align(Alignment.End),
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
    )
}
