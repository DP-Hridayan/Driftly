@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.calender.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import `in`.hridayan.driftly.core.presentation.components.haptic.withHaptic
import `in`.hridayan.driftly.core.presentation.theme.Shape
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun AddEditNoteDialog(
    initialDate: String,
    existingNote: SubjectNoteEntity? = null,
    onDismiss: () -> Unit,
    onSave: (date: String, note: String) -> Unit
) {
    var noteText by remember { mutableStateOf(existingNote?.note ?: "") }
    var selectedDateString by remember { mutableStateOf(existingNote?.date ?: initialDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    val weakHaptic = LocalWeakHaptic.current

    val parsedLocalDate = remember(selectedDateString) {
        try {
            LocalDate.parse(selectedDateString)
        } catch (e: Exception) {
            LocalDate.now()
        }
    }

    val displayDateFormatted = remember(parsedLocalDate) {
        parsedLocalDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(Shape.cardCornerLarge)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Notes,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = if (existingNote != null) stringResource(R.string.edit_note) else stringResource(R.string.add_note),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Date picker button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(onClick = withHaptic { showDatePicker = true }),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CalendarToday,
                        contentDescription = stringResource(R.string.date),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.date),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = displayDateFormatted,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Note input field
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text(text = stringResource(R.string.notes)) },
                placeholder = { Text(text = stringResource(R.string.note_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = MaterialTheme.shapes.medium,
                maxLines = 6
            )

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = withHaptic { onDismiss() },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(R.string.cancel))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = withHaptic {
                        if (noteText.isNotBlank()) {
                            onSave(selectedDateString, noteText)
                        }
                    },
                    enabled = noteText.isNotBlank(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }

    if (showDatePicker) {
        val initialEpochMillis = remember(parsedLocalDate) {
            parsedLocalDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()
        }
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialEpochMillis)

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = withHaptic {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedInstant = Instant.ofEpochMilli(millis)
                            val newLocalDate = selectedInstant.atZone(ZoneId.of("UTC")).toLocalDate()
                            selectedDateString = newLocalDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = withHaptic { showDatePicker = false }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
