@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.home.presentation.components.card

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.presentation.ui.theme.RobotoFlexBlack900

@SuppressLint("DefaultLocale")
@Composable
fun AttendanceOverviewCard(modifier: Modifier = Modifier, presentCount: Int, totalCount: Int) {
    val progress = presentCount.toFloat() / totalCount.toFloat()

    val safeTarget = progress.takeIf { !it.isNaN() }?.coerceIn(0f, 1f) ?: 0f

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(safeTarget) {
        animatedProgress.animateTo(
            targetValue = safeTarget,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
    }

    val progressText = "${String.format("%.0f", animatedProgress.value * 100)}%"

    val attendanceComment = when (progress) {
        in 0f..0.5f -> stringResource(R.string.attendance_comment_3)
        in 0.5f..0.75f -> stringResource(R.string.attendance_comment_2)
        else -> stringResource(R.string.attendance_comment_1)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                enabled = true,
                onClick = { }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.alpha(0.75f),
                text = stringResource(R.string.overall_attendance),
                style = MaterialTheme.typography.titleMediumEmphasized,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = progressText,
                fontFamily = RobotoFlexBlack900,
                fontSize = 80.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            LinearWavyProgressIndicator(
                progress = { animatedProgress.value },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = attendanceComment,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}