package `in`.hridayan.driftly.calender.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.toRoute
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.canvas.CalendarCanvas
import `in`.hridayan.driftly.calender.presentation.components.card.AttendanceCardWithTabs
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
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

    val onStatusChange: (String, AttendanceStatus?) -> Unit = { date, newStatus ->
        when (newStatus) {
            AttendanceStatus.PRESENT, AttendanceStatus.ABSENT -> {
                viewModel.upsert(
                    AttendanceEntity(subjectId = subjectId, date = date),
                    newStatus
                )
            }

            AttendanceStatus.UNMARKED -> {
                viewModel.clear(subjectId, date)
            }

            null -> viewModel.clear(subjectId, date)
        }
    }

    val selectedMonthYear = viewModel.selectedMonthYear.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = subject, overflow = TextOverflow.Ellipsis, maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        weakHaptic()
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
            )
        }) {

        Column(
            modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CalendarCanvas(
                modifier = Modifier.padding(horizontal = 15.dp),
                year = selectedMonthYear.year,
                month = selectedMonthYear.month.value,
                markedDates = markedDates,
                streakMap = streakMap,
                onStatusChange = onStatusChange,
                onNavigate = { newYear, newMonth ->
                    viewModel.updateMonthYear(newYear, newMonth)
                })

            AttendanceCardWithTabs(modifier = Modifier.weight(1f), subjectId = subjectId)
        }
    }
}
