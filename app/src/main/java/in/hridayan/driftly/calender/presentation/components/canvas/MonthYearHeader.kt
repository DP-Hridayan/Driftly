package `in`.hridayan.driftly.calender.presentation.components.canvas

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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