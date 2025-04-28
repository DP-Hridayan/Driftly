package `in`.hridayan.driftly.calender.presentation.components.menu

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus

@Composable
fun AttendanceDropDownMenu(
    modifier: Modifier = Modifier,
    onStatusChange: (date: String, status: AttendanceStatus?) -> Unit,
    dateString: String,
    expandedDateState: MutableState<String?>
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = {
            expandedDateState.value = null
        },
        modifier = modifier.background(Color.Transparent)
    ) {
        DropdownMenuItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            text = {
                Text(
                    text = stringResource(R.string.present),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            onClick = {
                onStatusChange(dateString, AttendanceStatus.PRESENT)
                expandedDateState.value = null
            }
        )
        DropdownMenuItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer),
            text = {
                Text(
                    text = stringResource(R.string.absent),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            },
            onClick = {
                onStatusChange(dateString, AttendanceStatus.ABSENT)
                expandedDateState.value = null
            }
        )
        DropdownMenuItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
            text = { Text(text = stringResource(R.string.clear)) },
            onClick = {
                onStatusChange(dateString, AttendanceStatus.UNMARKED)
                expandedDateState.value = null
            }
        )
    }
}