package `in`.hridayan.driftly.calender.presentation.components.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import `in`.hridayan.driftly.calender.presentation.viewmodel.CalendarViewModel
import `in`.hridayan.driftly.core.common.LocalSettings

@Composable
fun WeekDayLabels(
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val isMondayFirstDay = LocalSettings.current.startWeekOnMonday

    LaunchedEffect(isMondayFirstDay) {
        calendarViewModel.loadWeekdayLabels(isMondayFirstDay)
    }

    val labels = calendarViewModel.weekdayLabels.value

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        labels.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}
