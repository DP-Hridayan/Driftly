package `in`.hridayan.driftly.core.presentation.components.progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedHalfCircleProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    animationDuration: Int = 1000
) {
    val safeTarget = progress.takeIf { !it.isNaN() }?.coerceIn(0f, 1f) ?: 0f

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(safeTarget) {
        animatedProgress.animateTo(
            targetValue = safeTarget,
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

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val diameter = minOf(canvasWidth, canvasHeight * 2)
        val radius = diameter / 2f

        val topLeft = Offset(
            (canvasWidth - diameter) / 2f,
            (canvasHeight - radius)
        )

        val arcRect = Rect(
            topLeft,
            Size(diameter, diameter)
        )

        // Draw background arc
        drawArc(
            color = backgroundColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = arcRect.topLeft,
            size = arcRect.size,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        // Draw animated progress arc
        drawArc(
            color = progressColor,
            startAngle = 180f,
            sweepAngle = 180f * animatedProgress.value,
            useCenter = false,
            topLeft = arcRect.topLeft,
            size = arcRect.size,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
