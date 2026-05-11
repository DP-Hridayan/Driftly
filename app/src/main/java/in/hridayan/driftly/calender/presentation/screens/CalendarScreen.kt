package `in`.hridayan.driftly.calender.presentation.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.toRoute
import `in`.hridayan.driftly.calender.presentation.components.canvas.CalendarCanvas
import `in`.hridayan.driftly.calender.presentation.components.card.AttendanceCardWithTabs
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.presentation.components.button.BackButton
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
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
    val listState = rememberLazyListState()

    val onStatusChange: (String, AttendanceStatus?) -> Unit =
        { date, status ->
            viewModel.onStatusChange(subjectId, date, status)
        }

    LaunchedEffect(savedYear, savedMonth, shouldRememberMonthYear) {
        if (savedYear != null && savedMonth != null && shouldRememberMonthYear) {
            viewModel.updateMonthYear(savedYear, savedMonth)
        }
    }

    val classTypeTranslated = when (args?.classType) {
        "Theoretical" -> stringResource(R.string.theoretical)
        "Practical" -> stringResource(R.string.practical)
        else -> null
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
                        if (classTypeTranslated != null) {
                            Text(
                                text = classTypeTranslated,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                },
                navigationIcon = { BackButton() },
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
}
