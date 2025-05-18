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
import `in`.hridayan.driftly.core.common.LocalWeakHaptic

@Composable
fun MonthNavigationButtons(
    modifier: Modifier = Modifier, onNavigatePrev: () -> Unit,
    onNavigateNext: () -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        IconButton(onClick = {
            weakHaptic()
            onNavigatePrev()
        }) {
            Icon(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "Previous"
            )
        }

        IconButton(onClick = {
            weakHaptic()
            onNavigateNext()
        }) {
            Icon(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "Next"
            )
        }
    }
}