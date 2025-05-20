package `in`.hridayan.driftly.home.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SubjectText(modifier: Modifier = Modifier, subject: String, isLongClicked: Boolean = false) {
    Text(
        text = subject,
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500, easing = FastOutSlowInEasing
                )
            ),
        style = MaterialTheme.typography.titleMedium,
        color = if (isLongClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    )
}