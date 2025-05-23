package `in`.hridayan.driftly.calender.presentation.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus

@Composable
fun AttendanceDropDownMenu(
    modifier: Modifier = Modifier,
    onStatusChange: (date: String, status: AttendanceStatus?) -> Unit,
    dateString: String,
    expandedDateState: MutableState<String?>
) {
    val weakHaptic = LocalWeakHaptic.current
    DropdownMenu(
        expanded = true,
        onDismissRequest = {
            expandedDateState.value = null
        },
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .padding(4.dp),
    ) {
        DropdownMenuItem(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.primary),
            text = {
                Text(
                    text = stringResource(R.string.present),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Present",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            onClick = {
                weakHaptic()
                onStatusChange(dateString, AttendanceStatus.PRESENT)
                expandedDateState.value = null
            }
        )
        DropdownMenuItem(
            modifier = Modifier
                .padding(top = 5.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.error),
            text = {
                Text(
                    text = stringResource(R.string.absent),
                    color = MaterialTheme.colorScheme.onError
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Absent",
                    tint = MaterialTheme.colorScheme.onError
                )
            },
            onClick = {
                weakHaptic()
                onStatusChange(dateString, AttendanceStatus.ABSENT)
                expandedDateState.value = null
            }
        )
        DropdownMenuItem(
            modifier = Modifier
                .padding(top = 5.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.extraLarge
                ),
            text = { Text(text = stringResource(R.string.clear)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Remove,
                    contentDescription = stringResource(R.string.clear),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                weakHaptic()
                onStatusChange(dateString, AttendanceStatus.UNMARKED)
                expandedDateState.value = null
            }
        )
    }
}