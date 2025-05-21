package `in`.hridayan.driftly.core.presentation.components.canvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.math.sin

@Composable
fun VerticalProgressWave(
    modifier: Modifier = Modifier, progress: Float,
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

    Canvas(modifier = modifier.fillMaxWidth()) {
        val width = size.width
        val height = size.height

        if (height == 0f || width == 0f) return@Canvas

        val waveHeight = height * 0.1f
        val batteryLevelHeight = height * (1f - progress)

        val path = Path().apply {
            moveTo(0f, batteryLevelHeight)
            for (i in 0..width.toInt() step 1) {
                val x = i.toFloat()
                val y =
                    batteryLevelHeight + sin((x / width + animatedOffset) * waveFrequency * Math.PI).toFloat() * waveHeight
                lineTo(x, y)
            }
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(path = path, color = waveColor)
    }
}