package `in`.hridayan.driftly.calender.presentation.components.canvas

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.button.MonthNavigationButtons
import `in`.hridayan.driftly.calender.presentation.components.color.getAttendanceColors
import `in`.hridayan.driftly.calender.presentation.components.dialog.MonthYearPickerDialog
import `in`.hridayan.driftly.calender.presentation.components.menu.AttendanceDropDownMenu
import `in`.hridayan.driftly.calender.presentation.components.modifiers.streakModifier
import `in`.hridayan.driftly.calender.presentation.components.text.MonthYearHeader
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.StreakType
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarCanvas(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    markedDates: Map<LocalDate, AttendanceStatus>,
    streakMap: Map<LocalDate, StreakType>,
    onStatusChange: (date: String, status: AttendanceStatus?) -> Unit,
    onNavigate: (Int, Int) -> Unit,
    onResetMonth: () -> Unit,
) {
    val context = LocalContext.current
    val weakHaptic = LocalWeakHaptic.current
    val yearMonth = YearMonth.of(year, month)
    val today = LocalDate.now()
    val daysInMonth = yearMonth.lengthOfMonth()
    val isMondayFirstDay = LocalSettings.current.startWeekOnMonday
    val firstDayOfWeek =
        if (isMondayFirstDay) yearMonth.atDay(1).dayOfWeek.value - 1 else yearMonth.atDay(1).dayOfWeek.value % 7
    val abbreviatedMonth = yearMonth.format(
        DateTimeFormatter.ofPattern("MMM").withLocale(Locale.getDefault())
    )

    var showMonthYearDialog by remember { mutableStateOf(false) }
    val showStreakModifier = LocalSettings.current.showAttendanceStreaks
    val months: List<String> = context.resources.getStringArray(R.array.month_names).toList()

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 1000, easing = FastOutSlowInEasing
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        MonthYearHeader(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp),
            month = abbreviatedMonth,
            year = year
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MonthYearPicker(
                modifier = Modifier
                    .padding(start = 15.dp),
                fullMonthName = months[month - 1],
                year = year,
                onClick = {
                    showMonthYearDialog = true
                    weakHaptic()
                })

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            MonthNavigationButtons(
                modifier = Modifier.padding(end = 20.dp),
                onNavigatePrev = {
                    val prev = yearMonth.minusMonths(1)
                    onNavigate(prev.year, prev.monthValue)
                }, onNavigateNext = {
                    val next = yearMonth.plusMonths(1)
                    onNavigate(next.year, next.monthValue)
                },
                onReset = onResetMonth
            )
        }

        WeekDayLabels()

        val totalCells = ((firstDayOfWeek + daysInMonth + 6) / 7) * 7

        val expandedDateState = remember { mutableStateOf<String?>(null) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            userScrollEnabled = false
        ) {
            items((0 until totalCells).toList()) { index ->
                val day = index - firstDayOfWeek + 1
                val date = if (day in 1..daysInMonth) yearMonth.atDay(day) else null
                val dateString = date?.format(DateTimeFormatter.ISO_DATE)

                if (dateString != null) {
                    val status = markedDates[date] ?: AttendanceStatus.UNMARKED
                    val streakType = streakMap[date] ?: StreakType.NONE

                    val attendanceColors = getAttendanceColors(status)
                    val backgroundColor = attendanceColors.background
                    val foregroundColor = attendanceColors.foreground

                    val animatedProgress = remember(dateString, status) { Animatable(0f) }
                    val animatedScale = when (status) {
                        AttendanceStatus.UNMARKED -> 1f
                        else -> animatedProgress.value
                    }

                    LaunchedEffect(dateString, status) {
                        if (status != AttendanceStatus.UNMARKED) {
                            animatedProgress.animateTo(
                                targetValue = 1f, animationSpec = tween(
                                    durationMillis = 1000, easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }

                    val isToday =
                        today.year == year && today.monthValue == month && today.dayOfMonth == day

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .scale(animatedScale)
                            .streakModifier(
                                showStreak = showStreakModifier,
                                streakType = streakType,
                                circleBg = backgroundColor,
                                circleFg = foregroundColor,
                                streakBandColor = foregroundColor,
                                isToday = isToday
                            )
                            .clickable {
                                expandedDateState.value = dateString
                                weakHaptic()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (expandedDateState.value == dateString) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .size(4.dp)
                                    .then(
                                        if (streakType == StreakType.MIDDLE) {
                                            Modifier
                                                .offset(y = 4.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    color = backgroundColor, shape = CircleShape
                                                )
                                        } else Modifier
                                            .offset(y = 3.dp)
                                            .clip(CircleShape)
                                            .background(
                                                color = foregroundColor, shape = CircleShape
                                            )
                                    )
                            )
                        }

                        Text(
                            text = "$day",
                            color = if (streakType == StreakType.MIDDLE && showStreakModifier) backgroundColor else foregroundColor,
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (expandedDateState.value == dateString) {
                            AttendanceDropDownMenu(
                                onStatusChange = { date, status ->
                                    onStatusChange(date, status)
                                    expandedDateState.value = null
                                },
                                dateString = dateString,
                                modifier = Modifier,
                                expandedDateState = expandedDateState,
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                    )
                }
            }
        }
    }

    if (showMonthYearDialog) {
        MonthYearPickerDialog(
            yearDisplayed = year,
            monthDisplayed = month,
            onDismiss = { showMonthYearDialog = false },
            onConfirm = { month, year ->
                onNavigate(year, month)
            })
    }
}