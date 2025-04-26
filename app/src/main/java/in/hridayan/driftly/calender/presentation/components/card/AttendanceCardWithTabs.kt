package `in`.hridayan.driftly.calender.presentation.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.toRoute
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.home.presentation.viewmodel.AttendanceCounts
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewmodel
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.NavControllerHolder
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape
import kotlinx.coroutines.launch

enum class AttendanceDataTabs(
    val text: String
) {
    THIS_MONTH("This Month"),
    ALL_MONTHS("All Months")
}

@Composable
fun AllMonthsView(viewModel: HomeViewmodel = hiltViewModel(), subjectId: Int) {
    val counts by viewModel.getAttendanceCounts(subjectId)
        .collectAsState(initial = AttendanceCounts())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("All Months Data", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Present: ${counts.presentCount}")
        Text("Absent: ${counts.absentCount}")
        Text("Total: ${counts.totalCount}")
    }
}

@Composable
fun ThisMonthView(viewModel: CalendarViewModel = hiltViewModel(), subjectId: Int) {
    val selectedMonthYear = viewModel.selectedMonthYear.value
    val counts by viewModel.getMonthlyAttendanceCounts(
        subjectId,
        selectedMonthYear.year,
        selectedMonthYear.month.value
    )
        .collectAsState(initial = AttendanceCounts())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This Month Data", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Present: ${counts.presentCount}")
        Text("Absent: ${counts.absentCount}")
        Text("Total: ${counts.totalCount}")
    }
}

@Composable
fun AttendanceCardWithTabs(modifier: Modifier = Modifier) {
    val tabs = listOf(AttendanceDataTabs.THIS_MONTH, AttendanceDataTabs.ALL_MONTHS)
    val pagerState = rememberPagerState(pageCount = { AttendanceDataTabs.entries.size })
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(Shape.cardCornerLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                AttendanceDataTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = index == selectedTabIndex.value,
                        selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = currentTab.text,
                                color = if (index == selectedTabIndex.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }
                    )
                }
            }

            val args =
                NavControllerHolder.navController?.currentBackStackEntry?.toRoute<CalendarScreen>()
            val subjectId = args?.subjectId ?: 0
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (index) {
                        0 -> ThisMonthView(subjectId = subjectId)
                        1 -> AllMonthsView(subjectId = subjectId)
                    }
                }
            }
        }
    }
}
