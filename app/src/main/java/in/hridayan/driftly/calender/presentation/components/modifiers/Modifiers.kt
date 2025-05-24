package `in`.hridayan.driftly.calender.presentation.components.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.domain.model.StreakType

fun Modifier.streakModifier(
    showStreak: Boolean = true,
    streakType: StreakType,
    circleBg: Color,
    circleFg: Color,
    streakBandColor: Color,
    isToday: Boolean,
): Modifier {
    val baseCircleModifier = this
        .padding(4.dp)
        .clip(CircleShape)
        .background(circleBg)
        .then(
            if (isToday) Modifier.border(1.dp, circleFg, CircleShape) else Modifier
        )

    if (!showStreak) return baseCircleModifier

    else return when (streakType) {
            StreakType.START -> this
                .drawBehind {
                    val paddingY = 8.dp.toPx()
                    val startX = size.width * 0.5f
                    drawRect(
                        color = streakBandColor,
                        topLeft = Offset(startX, paddingY),
                        size = Size(size.width - startX, size.height - 2 * paddingY)
                    )
                }
                .then(baseCircleModifier)

            StreakType.END -> this
                .drawBehind {
                    val paddingY = 8.dp.toPx()
                    val endX = size.width * 0.5f
                    drawRect(
                        color = streakBandColor,
                        topLeft = Offset(0f, paddingY),
                        size = Size(endX, size.height - 2 * paddingY)
                    )
                }
                .then(baseCircleModifier)

            StreakType.MIDDLE -> this
                .padding(vertical = 8.dp)
                .background(streakBandColor)
                .then(
                    if (isToday) Modifier
                        .padding(horizontal = 8.dp)
                        .border(1.dp, circleBg, CircleShape)
                    else Modifier
                )

            StreakType.NONE -> baseCircleModifier
        }
}