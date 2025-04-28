package `in`.hridayan.driftly.core.presentation.components.progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCircularProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float ,
    animationDuration: Int = 1000
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        if(!progress.isNaN())
        animatedProgress.animateTo(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
        )
    }

    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = animatedProgress.value
    )

    CircularProgressIndicator(
        progress = { animatedProgress.value },
        modifier = modifier,
        color = progressColor,
        strokeWidth = 3.dp,
        trackColor = MaterialTheme.colorScheme.secondaryContainer,
        strokeCap = StrokeCap.Round,
    )
}