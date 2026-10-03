package `in`.hridayan.driftly.calender.presentation.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.toRoute
import `in`.hridayan.driftly.calender.presentation.components.canvas.CalendarCanvas
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.card.AttendanceCardWithTabs
import `in`.hridayan.driftly.calender.presentation.components.dialog.AddEditNoteDialog
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarUiEvent
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectClassType
import `in`.hridayan.driftly.core.domain.provider.classTypeToString
import `in`.hridayan.driftly.core.presentation.components.button.BackButton
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.LocalNavController

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Unarchive
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val args = navController.currentBackStackEntry?.toRoute<CalendarScreen>()
    val subjectId = args?.subjectId ?: 0
    val subject = args?.subject ?: ""
    val classType = args?.classType ?: SubjectClassType.NONE
    val markedDates by viewModel.markedDatesFlow.collectAsState()
    val streakMap by viewModel.streakMapFlow.collectAsState(initial = emptyMap())
    val datesWithNotes by viewModel.datesWithNotesFlow.collectAsState()
    val subjectEntity = viewModel.getSubjectEntityById(subjectId).collectAsState(initial = null)
    val savedYear = subjectEntity.value?.savedYear
    val savedMonth = subjectEntity.value?.savedMonth
    val monthYear = viewModel.selectedMonthYear.value
    val year = monthYear.year
    val month = monthYear.monthValue
    val shouldRememberMonthYear = LocalSettings.current.rememberCalendarMonthYear
    val listState = rememberLazyListState()
    val noClassScheduledMsg = stringResource(R.string.no_class_scheduled)
    var noteDialogDate by remember { mutableStateOf<String?>(null) }

    val onStatusChange: (String, AttendanceStatus?) -> Unit =
        { date, status ->
            viewModel.onStatusChange(subjectId, date, status, noClassScheduledMsg)
        }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CalendarUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(savedYear, savedMonth, shouldRememberMonthYear) {
        if (savedYear != null && savedMonth != null && shouldRememberMonthYear) {
            viewModel.updateMonthYear(savedYear, savedMonth)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .basicMarquee(),
                            text = subject, overflow = TextOverflow.Ellipsis, maxLines = 1
                        )
                        val classTypeText = classTypeToString(context, classType)

                        if (classType != SubjectClassType.NONE) {
                            Text(
                                text = classTypeText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                },
                navigationIcon = { BackButton() },
                actions = {
                    val isArchived = subjectEntity.value?.isArchived ?: false
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = if (isArchived) stringResource(R.string.archived) else stringResource(R.string.archive),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isArchived) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Switch(
                            checked = isArchived,
                            onCheckedChange = {
                                viewModel.toggleSubjectArchived(subjectId, isArchived)
                            },
                            thumbContent = {
                                Icon(
                                    imageVector = if (isArchived) Icons.Rounded.Archive else Icons.Rounded.Unarchive,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize)
                                )
                            }
                        )
                    }
                }
            )
        }) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                CalendarCanvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    year = year,
                    month = month,
                    markedDates = markedDates,
                    streakMap = streakMap,
                    datesWithNotes = datesWithNotes,
                    onStatusChange = onStatusChange,
                    onAddNote = { date -> noteDialogDate = date },
                    onNavigate = { newYear, newMonth ->
                        viewModel.updateMonthYear(newYear, newMonth)
                        viewModel.saveMonthYearForSubject(subjectId)
                    },
                    onResetMonth = {
                        viewModel.resetYearMonthToCurrent()
                        viewModel.saveMonthYearForSubject(subjectId)
                    }
                )
            }

            item {
                AttendanceCardWithTabs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(300.dp),
                    subjectId = subjectId
                )
            }
        }
    }

    noteDialogDate?.let { date ->
        AddEditNoteDialog(
            initialDate = date,
            onDismiss = { noteDialogDate = null },
            onSave = { dateStr, noteText ->
                viewModel.addNote(subjectId, dateStr, noteText)
                noteDialogDate = null
            }
        )
    }
}
