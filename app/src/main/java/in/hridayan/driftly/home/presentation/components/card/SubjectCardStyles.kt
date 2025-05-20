package `in`.hridayan.driftly.home.presentation.components.card

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.components.progress.CircularProgressWithText
import `in`.hridayan.driftly.home.presentation.components.SubjectText

@Composable
fun CardStyleA(
    modifier: Modifier = Modifier,
    subject: String,
    progress: Float,
    isLongClicked: Boolean,
    isTotalCountZero: Boolean,
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onErrorIconClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500, easing = FastOutSlowInEasing
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        SubjectText(
            modifier = Modifier.weight(1f),
            subject = subject,
            isLongClicked = isLongClicked
        )

        if (isLongClicked) {
            UtilityRow(
                onEditButtonClicked = onEditButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
            )
        } else {
            if (isTotalCountZero) {
                ErrorIcon(onClick = onErrorIconClicked)
            } else {
                CircularProgressWithText(progress = progress)
            }
        }
    }
}

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit,
    isLongClicked: Boolean,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .combinedClickable(
                enabled = true,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = if (isLongClicked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        content()
    }
}

