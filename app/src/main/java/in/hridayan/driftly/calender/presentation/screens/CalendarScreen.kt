package `in`.hridayan.driftly.calender.presentation.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.toRoute
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.bottomsheet.SubjectAttendanceDataBottomSheet
import `in`.hridayan.driftly.calender.presentation.components.canvas.CalendarCanvas
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.presentation.components.button.BackButton
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val weakHaptic = LocalWeakHaptic.current
    val navController = LocalNavController.current
    val args = navController.currentBackStackEntry?.toRoute<CalendarScreen>()
    val subjectId = args?.subjectId ?: 0
    val subject = args?.subject ?: ""
    val markedDates by viewModel.markedDatesFlow.collectAsState()
    val streakMap by viewModel.streakMapFlow.collectAsState(initial = emptyMap())
    val subjectEntity = viewModel.getSubjectEntityById(subjectId).collectAsState(initial = null)
    val savedYear = subjectEntity.value?.savedYear
    val savedMonth = subjectEntity.value?.savedMonth
    val monthYear = viewModel.selectedMonthYear.value
    val year = monthYear.year
    val month = monthYear.monthValue
    val shouldRememberMonthYear = LocalSettings.current.rememberCalendarMonthYear
    var showSubjectAttendanceDataBottomSheet by rememberSaveable { mutableStateOf(false) }

    val onStatusChange: (String, AttendanceStatus?) -> Unit =
        { date, status ->
            viewModel.onStatusChange(subjectId, date, status)
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
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee(),
                        text = subject, overflow = TextOverflow.Ellipsis, maxLines = 1
                    )
                },
                navigationIcon = { BackButton() },
            )
        }) {

        Column(
            modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CalendarCanvas(
                modifier = Modifier.padding(horizontal = 15.dp),
                year = year,
                month = month,
                markedDates = markedDates,
                streakMap = streakMap,
                onStatusChange = onStatusChange,
                onNavigate = { newYear, newMonth ->
                    viewModel.updateMonthYear(newYear, newMonth)
                    viewModel.saveMonthYearForSubject(subjectId)
                },
                onResetMonth = {
                    viewModel.resetYearMonthToCurrent()
                    viewModel.saveMonthYearForSubject(subjectId)
                }
            )

            Button(
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    weakHaptic()
                    showSubjectAttendanceDataBottomSheet = true
                }) {
                AutoResizeableText(stringResource(R.string.attendance_overview))
            }

            if (showSubjectAttendanceDataBottomSheet) {
                SubjectAttendanceDataBottomSheet(
                    onDismiss = {
                        showSubjectAttendanceDataBottomSheet = false
                    },
                    subjectId = subjectId
                )
            }
        }
    }
}
