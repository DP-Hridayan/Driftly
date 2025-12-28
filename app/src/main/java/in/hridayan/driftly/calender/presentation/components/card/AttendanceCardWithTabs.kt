@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.calender.presentation.components.card

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.image.UndrawDatePicker
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.presentation.components.progress.AnimatedHalfCircleProgress
import `in`.hridayan.driftly.core.presentation.theme.Shape
import `in`.hridayan.driftly.home.presentation.components.label.Label
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun AttendanceCardWithTabs(
    modifier: Modifier = Modifier,
    subjectId: Int,
    sheetState: SheetState
) {
    val attendanceDataTabs =
        listOf(
            stringResource(R.string.this_month_data),
            stringResource(R.string.all_months_data)
        )

    val pagerState = rememberPagerState(pageCount = { attendanceDataTabs.size })
    val coroutineScope = rememberCoroutineScope()
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val weakHaptic = LocalWeakHaptic.current

    Card(
        modifier = modifier
            .clip(Shape.cardCornerLarge)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(containerColor = BottomSheetDefaults.ContainerColor)
    ) {
        Column {
            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex.value,
                containerColor = BottomSheetDefaults.ContainerColor,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                attendanceDataTabs.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = index == selectedTabIndex.value,
                        selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(attendanceDataTabs.indexOf(currentTab))
                                weakHaptic()
                            }
                        },
                        text = {
                            Text(
                                text = currentTab,
                                color = if (index == selectedTabIndex.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { index ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (index) {
                        0 -> ThisMonthView(
                            modifier = Modifier.padding(25.dp),
                            subjectId = subjectId,
                            sheetState = sheetState
                        )

                        1 -> AllMonthsView(
                            modifier = Modifier.padding(25.dp),
                            subjectId = subjectId,
                            sheetState = sheetState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AllMonthsView(
    modifier: Modifier = Modifier,
    subjectId: Int,
    sheetState: SheetState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val counts by viewModel.getSubjectAttendanceCounts(subjectId)
        .collectAsState(initial = SubjectAttendance())

    val progress = counts.presentCount.toFloat() / counts.totalCount.toFloat()

    if (counts.totalCount == 0) {
        UndrawDatePicker(modifier = modifier)
    } else {
        ProgressView(
            modifier = modifier,
            counts = counts,
            progress = progress,
            sheetState = sheetState
        )
    }
}

@Composable
private fun ThisMonthView(
    modifier: Modifier = Modifier,
    subjectId: Int,
    sheetState: SheetState,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val selectedMonthYear = viewModel.selectedMonthYear.value
    val counts by viewModel.getMonthlyAttendanceCounts(
        subjectId,
        selectedMonthYear.year,
        selectedMonthYear.month.value
    )
        .collectAsState(initial = SubjectAttendance())
    val progress = counts.presentCount.toFloat() / counts.totalCount.toFloat()

    if (counts.totalCount == 0) {
        UndrawDatePicker(modifier = modifier)
    } else {
        ProgressView(
            modifier = modifier,
            counts = counts,
            progress = progress,
            sheetState = sheetState
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun ProgressView(
    modifier: Modifier = Modifier,
    counts: SubjectAttendance,
    progress: Float,
    sheetState: SheetState
) {
    val progressText = "${String.format("%.0f", progress * 100)}%"

    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = progress.coerceIn(0f, 1f)
    )

    val showAnimation = remember { mutableStateOf(false) }

    LaunchedEffect(sheetState) {
        if (sheetState.currentValue == SheetValue.Expanded) {
            showAnimation.value = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Box {
            AnimatedHalfCircleProgress(
                progress = progress,
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp),
                animationDuration = if (showAnimation.value) 3000 else 0
            )

            Text(
                text = progressText,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = progressColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Label(
                text = "${stringResource(R.string.present)}: ${counts.presentCount}",
                labelColor = MaterialTheme.colorScheme.primaryContainer,
                strokeColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                animationDuration = if (showAnimation.value) 600 else 0
            )
            Label(
                text = "${stringResource(R.string.absent)}: ${counts.absentCount}",
                labelColor = MaterialTheme.colorScheme.errorContainer,
                strokeColor = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                animationDuration = if (showAnimation.value) 600 else 0
            )

            Label(
                text = "${stringResource(R.string.total)}: ${counts.totalCount}",
                labelColor = MaterialTheme.colorScheme.tertiaryContainer,
                strokeColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                animationDuration = if (showAnimation.value) 600 else 0
            )
        }
    }
}