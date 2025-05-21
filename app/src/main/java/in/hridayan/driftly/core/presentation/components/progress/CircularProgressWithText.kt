package `in`.hridayan.driftly.core.presentation.components.progress

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.sp

@SuppressLint("DefaultLocale")
@Composable
fun CircularProgressWithText(modifier: Modifier = Modifier, progress: Float) {
    val progressText = "${String.format("%.0f", progress * 100)}%"
    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = progress.coerceIn(0f, 1f)
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        AnimatedCircularProgressIndicator(
            progress = progress, animationDuration = 3000
        )
        Text(
            text = progressText,
            color = progressColor,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp
            )
        )
    }
}