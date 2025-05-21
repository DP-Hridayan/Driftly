package `in`.hridayan.driftly.core.presentation.components.canvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.sin

@Composable
fun HorizontalProgressWave(
    modifier: Modifier = Modifier,
    progress: Float,
    waveSpeed: Int = 4000,
    waveFrequency: Float = 2f,
    waveColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
) {
    val animatedOffset by rememberInfiniteTransition(label = "wave")
        .animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(animation = tween(waveSpeed, easing = LinearEasing)),
            label = "Wave Offset"
        )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val waveAmplitude = height * 0.05f
        val batteryLevelWidth = width * progress.coerceIn(0f, 1f)

        val path = Path().apply {
            moveTo(0f, 0f)

            for (i in 0..height.toInt()) {
                val y = i.toFloat()
                val x =
                    batteryLevelWidth + sin((y / height + animatedOffset) * waveFrequency * Math.PI).toFloat() * waveAmplitude
                lineTo(x, y)
            }

            lineTo(0f, height)
            close()
        }

        drawPath(path = path, color = waveColor)
    }
}