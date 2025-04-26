package `in`.hridayan.driftly.home.components.card

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.components.progress.AnimatedCircularProgressIndicator
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.navigateTo
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape

@SuppressLint("DefaultLocale")
@Composable
fun SubjectCard(modifier: Modifier = Modifier, subjectId: Int, subject: String, progress: Float) {

    val progressText = "${String.format("%.0f", progress*100)}%"

    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = progress.coerceIn(0f, 1f)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(Shape.cardCornerSmall)
            .clickable(enabled = true, onClick = {
                navigateTo(
                    CalendarScreen(
                        subjectId = subjectId,
                        subject = subject,
                    )
                )
            }),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = subject, modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleMedium
            )

            Box(contentAlignment = Alignment.Center) {
                AnimatedCircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    progress = progress,
                    animationDuration = 3000
                )

                Text(
                    text = progressText,
                    color = progressColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}