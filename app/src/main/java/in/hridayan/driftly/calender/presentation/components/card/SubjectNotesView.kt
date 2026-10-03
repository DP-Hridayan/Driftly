package `in`.hridayan.driftly.calender.presentation.components.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.EventNote
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.dialog.AddEditNoteDialog
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import `in`.hridayan.driftly.core.presentation.components.haptic.withHaptic
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectNotesView(
    modifier: Modifier = Modifier,
    subjectId: Int,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val notes by viewModel.notesFlow.collectAsState()
    val selectedMonthYear = viewModel.selectedMonthYear.value
    val weakHaptic = LocalWeakHaptic.current

    var filterThisMonthOnly by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<SubjectNoteEntity?>(null) }

    val filteredNotes = remember(notes, filterThisMonthOnly, selectedMonthYear) {
        if (!filterThisMonthOnly) {
            notes
        } else {
            notes.filter { note ->
                try {
                    val parsed = LocalDate.parse(note.date)
                    parsed.year == selectedMonthYear.year && parsed.monthValue == selectedMonthYear.monthValue
                } catch (e: Exception) {
                    false
                }
            }
        }
    }

    val defaultDateString = remember(selectedMonthYear) {
        val today = LocalDate.now()
        if (today.year == selectedMonthYear.year && today.monthValue == selectedMonthYear.monthValue) {
            today.format(DateTimeFormatter.ISO_LOCAL_DATE)
        } else {
            selectedMonthYear.atDay(1).format(DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with Filter chips and Add Note button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = !filterThisMonthOnly,
                    onClick = withHaptic { filterThisMonthOnly = false },
                    label = { Text(text = stringResource(R.string.all_notes)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                FilterChip(
                    selected = filterThisMonthOnly,
                    onClick = withHaptic { filterThisMonthOnly = true },
                    label = {
                        val monthName = selectedMonthYear.month.name.lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                        Text(text = "$monthName ${selectedMonthYear.year}")
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            ElevatedButton(
                onClick = withHaptic { showAddDialog = true },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(R.string.add_note),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.add_note),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // Notes List or Empty State
        if (filteredNotes.isEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.EventNote,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                    Text(
                        text = stringResource(R.string.no_notes_yet),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                filteredNotes.forEach { note ->
                    SubjectNoteItemCard(
                        note = note,
                        onEdit = { editingNote = note },
                        onDelete = {
                            weakHaptic()
                            viewModel.deleteNote(note)
                        }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddEditNoteDialog(
            initialDate = defaultDateString,
            onDismiss = { showAddDialog = false },
            onSave = { date, text ->
                viewModel.addNote(subjectId, date, text)
                showAddDialog = false
            }
        )
    }

    editingNote?.let { note ->
        AddEditNoteDialog(
            initialDate = note.date,
            existingNote = note,
            onDismiss = { editingNote = null },
            onSave = { date, text ->
                viewModel.updateNote(note.copy(date = date, note = text))
                editingNote = null
            }
        )
    }
}

@Composable
private fun SubjectNoteItemCard(
    note: SubjectNoteEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current
    val parsedDate = remember(note.date) {
        try {
            LocalDate.parse(note.date)
        } catch (e: Exception) {
            null
        }
    }

    val monthName = parsedDate?.month?.name?.take(3) ?: ""
    val dayNumber = parsedDate?.dayOfMonth?.toString() ?: ""
    val yearNumber = parsedDate?.year?.toString() ?: ""
    val fullFormattedDate = parsedDate?.format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    ) ?: note.date

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Prominent Month & Date badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(width = 56.dp, height = 62.dp)
            ) {
                Column(
                    modifier = Modifier.padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = monthName.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 11.sp
                    )
                    Text(
                        text = dayNumber,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        lineHeight = 22.sp
                    )
                    Text(
                        text = yearNumber,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Note text and date detail
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = fullFormattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = note.note,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Edit and Delete buttons
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = withHaptic { onEdit() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(R.string.edit_note),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = withHaptic { onDelete() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DeleteOutline,
                        contentDescription = stringResource(R.string.delete_note),
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
