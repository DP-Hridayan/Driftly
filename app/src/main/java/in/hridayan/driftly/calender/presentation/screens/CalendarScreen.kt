package `in`.hridayan.driftly.calender.presentation.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.toRoute
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
    val lifecycleOwner = LocalLifecycleOwner.current
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

    val subjectEntity = viewModel.getSubjectEntityById(subjectId).collectAsState(initial = null)
    val savedYear = subjectEntity.value?.savedYear
    val savedMonth = subjectEntity.value?.savedMonth
    var monthYear = viewModel.selectedMonthYear.value
    val year = monthYear.year
    val month = monthYear.monthValue

    LaunchedEffect(savedYear, savedMonth) {
        if (savedYear != null && savedMonth != null) viewModel.updateMonthYear(
            savedYear,
            savedMonth
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.saveMonthYearForSubject(subjectId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
                navigationIcon = {
                    IconButton(onClick = {
                        weakHaptic()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
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
                year = year,
                month = month,
                markedDates = markedDates,
                streakMap = streakMap,
                onStatusChange = onStatusChange,
                onNavigate = { newYear, newMonth ->
                    viewModel.updateMonthYear(newYear, newMonth)
                },
            )

            AttendanceCardWithTabs(modifier = Modifier.weight(1f), subjectId = subjectId)
        }
    }
}
