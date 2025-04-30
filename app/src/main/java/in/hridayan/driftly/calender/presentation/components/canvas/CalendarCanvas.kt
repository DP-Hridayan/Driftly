package `in`.hridayan.driftly.calender.presentation.components.canvas

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.calender.presentation.components.dialog.MonthYearPickerDialog
import `in`.hridayan.driftly.calender.presentation.components.menu.AttendanceDropDownMenu
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

@Composable
fun CalendarCanvas(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    markedDates: Map<String, AttendanceStatus>,
    onStatusChange: (date: String, status: AttendanceStatus?) -> Unit,
    onNavigate: (Int, Int) -> Unit
) {
    val yearMonth = YearMonth.of(year, month)
    val today = LocalDate.now()
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7
    val fullMonthName = yearMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val abbreviatedMonth = yearMonth.format(
        DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH)
    )

    var showMonthYearDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "$abbreviatedMonth, $year",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .animateContentSize()
                .padding(horizontal = 20.dp)
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            showMonthYearDialog = true
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$fullMonthName ${yearMonth.year}",
                    style = MaterialTheme.typography.titleSmall
                )

                IconButton(onClick = {
                    showMonthYearDialog = true
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_dropdown),
                        contentDescription = "Dropdown icon"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                IconButton(onClick = {
                    val prev = yearMonth.minusMonths(1)
                    onNavigate(prev.year, prev.monthValue)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = "Previous"
                    )
                }

                IconButton(onClick = {
                    val next = yearMonth.plusMonths(1)
                    onNavigate(next.year, next.monthValue)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_right),
                        contentDescription = "Next"
                    )
                }
            }
        }



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

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
                val dateString = if (day in 1..daysInMonth)
                    yearMonth.atDay(day).format(DateTimeFormatter.ISO_DATE)
                else null

                if (dateString != null) {
                    val status = markedDates[dateString] ?: AttendanceStatus.UNMARKED
                    val bgColor = when (status) {
                        AttendanceStatus.PRESENT -> MaterialTheme.colorScheme.primaryContainer
                        AttendanceStatus.ABSENT -> MaterialTheme.colorScheme.errorContainer
                        AttendanceStatus.UNMARKED -> MaterialTheme.colorScheme.surface
                    }
                    val animatedProgress = remember(dateString, status) { Animatable(0f) }
                    val randomDuration = Random.nextInt(500, 1500)
                    val animatedScale = when (status) {
                        AttendanceStatus.UNMARKED -> 1f
                        else -> animatedProgress.value
                    }

                    LaunchedEffect(dateString, status) {
                        if (status != AttendanceStatus.UNMARKED) {
                            animatedProgress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = randomDuration,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }

                    val isToday =
                        today.year == year && today.monthValue == month && today.dayOfMonth == day

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .scale(animatedScale)
                            .clip(CircleShape)
                            .background(bgColor)
                            .then(
                                if (isToday) Modifier.border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ) else Modifier
                            )
                            .clickable {
                                expandedDateState.value = dateString
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (expandedDateState.value == dateString) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .size(4.dp)
                                    .offset(y = 3.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        shape = CircleShape
                                    )
                            )
                        }

                        Text(
                            text = "$day",
                            color = MaterialTheme.colorScheme.onSurface,
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
            }
        )
    }
}