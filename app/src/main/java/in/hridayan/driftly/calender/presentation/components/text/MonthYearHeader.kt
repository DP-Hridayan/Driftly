package `in`.hridayan.driftly.calender.presentation.components.text

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MonthYearHeader(
    modifier: Modifier = Modifier,
    month: String, year: Int
) {
    Text(
        text = "$month, $year",
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
            .animateContentSize()
    )
}