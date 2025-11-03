package `in`.hridayan.driftly.home.presentation.components.label

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.theme.Shape
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String,
    labelColor: Color,
    strokeColor: Color,
    onClick: () -> Unit = {}
) {
    val weakHaptic = LocalWeakHaptic.current
    val animatedScale = remember { Animatable(0f) }
    val randomDelay = remember { Random.nextInt(250, 750) }

    LaunchedEffect(text) {
        delay(randomDelay.toLong())
        animatedScale.animateTo(
            targetValue = 1f.coerceIn(0.3f, 1f),
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .scale(animatedScale.value)
            .clip(Shape.cardCornerLarge)
            .background(labelColor)
            .border(
                width = Shape.labelStroke,
                shape = Shape.cardCornerLarge,
                color = strokeColor,
            )
            .clickable(enabled = true, onClick = {
                onClick()
                weakHaptic()
            })
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        AutoResizeableText(
            text = text,
            color = strokeColor,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}