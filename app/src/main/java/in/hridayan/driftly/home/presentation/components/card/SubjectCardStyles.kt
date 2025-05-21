package `in`.hridayan.driftly.home.presentation.components.card

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.core.presentation.components.canvas.VerticalProgressWave
import `in`.hridayan.driftly.core.presentation.components.progress.CircularProgressWithText
import `in`.hridayan.driftly.home.presentation.components.text.SubjectText

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
    val subjectTextColor =
        if (isLongClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    val backgroundColor =
        if (isLongClicked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500, easing = FastOutSlowInEasing
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        SubjectText(
            modifier = Modifier.weight(1f), subject = subject, subjectTextColor = subjectTextColor
        )

        if (isLongClicked) {
            UtilityRow(
                onEditButtonClicked = onEditButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
            )
        } else {
            if (isTotalCountZero) ErrorIcon(onClick = onErrorIconClicked)
            else CircularProgressWithText(progress = progress)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CardStyleB(
    modifier: Modifier = Modifier,
    progress: Float,
    subject: String,
    isLongClicked: Boolean,
    isTotalCountZero: Boolean,
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onErrorIconClicked: () -> Unit,
) {
    val progressText = "${String.format("%.0f", progress * 100)}%"

    var contentHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        VerticalProgressWave(
            modifier = Modifier.height(with(LocalDensity.current) { contentHeightPx.toDp() }),
            progress = progress,
            waveSpeed = 4000
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { contentHeightPx = it.height },
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500, easing = FastOutSlowInEasing
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                SubjectText(
                    modifier = Modifier.weight(1f), subject = subject
                )

                if (isLongClicked) {
                    UtilityRow(
                        onEditButtonClicked = onEditButtonClicked,
                        onDeleteButtonClicked = onDeleteButtonClicked
                    )
                } else {
                    if (isTotalCountZero) ErrorIcon(onClick = onErrorIconClicked)
                    else Text(
                        text = progressText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
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
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .combinedClickable(
                enabled = true, onClick = onClick, onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(cornerRadius),
    ) {
        content()
    }
}

