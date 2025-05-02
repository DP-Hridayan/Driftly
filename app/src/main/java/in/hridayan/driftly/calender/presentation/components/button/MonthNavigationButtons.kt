package `in`.hridayan.driftly.calender.presentation.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R

@Composable
fun  MonthNavigationButtons(modifier: Modifier = Modifier, onNavigatePrev: () -> Unit,
                            onNavigateNext: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        IconButton(onClick = onNavigatePrev) {
            Icon(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "Previous"
            )
        }

        IconButton(onClick = onNavigateNext) {
            Icon(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "Next"
            )
        }
    }
}