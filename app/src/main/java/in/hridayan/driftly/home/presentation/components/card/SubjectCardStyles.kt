package `in`.hridayan.driftly.home.presentation.components.card

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectClassType
import `in`.hridayan.driftly.core.domain.provider.classTypeToString
import `in`.hridayan.driftly.core.presentation.components.canvas.VerticalProgressWave
import `in`.hridayan.driftly.core.presentation.components.progress.CircularProgressWithText
import `in`.hridayan.driftly.home.presentation.components.text.SubjectText

@Composable
fun CardStyleA(
    modifier: Modifier = Modifier,
    subject: String,
    room: String? = null,
    classType: SubjectClassType = SubjectClassType.NONE,
    progress: Float,
    isLongClicked: Boolean,
    isTotalCountZero: Boolean,
    isArchived: Boolean = false,
    isClassScheduledToday: Boolean = false,
    todayStatus: AttendanceStatus? = null,
    onMarkPresent: () -> Unit = {},
    onMarkAbsent: () -> Unit = {},
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onErrorIconClicked: () -> Unit,
) {
    val context = LocalContext.current
    val subjectTextColor =
        if (isLongClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    val backgroundColor =
        if (isLongClicked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500, easing = FastOutSlowInEasing
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SubjectText(
                    subject = subject,
                    subjectTextColor = subjectTextColor,
                    modifier = Modifier.weight(1f, fill = false)
                )
                if (isArchived) {
                    ArchivedIndicator()
                }
            }

            val classTypeText = classTypeToString(
                context,
                classType
            ).takeUnless { classType == SubjectClassType.NONE }

            val roomText = room?.takeIf { it.isNotBlank() }

            val infoText = listOfNotNull(
                roomText,
                classTypeText
            ).joinToString(" | ")

            if (infoText.isNotEmpty()) {
                Text(
                    text = infoText,
                    style = MaterialTheme.typography.bodySmall,
                    color = subjectTextColor.copy(alpha = 0.7f)
                )
            }
        }

        if (isLongClicked) {
            UtilityRow(
                onEditButtonClicked = onEditButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
            )
        } else {
            if (isClassScheduledToday) {
                QuickAttendanceButtons(
                    todayStatus = todayStatus,
                    onMarkPresent = onMarkPresent,
                    onMarkAbsent = onMarkAbsent
                )
            }

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
    room: String? = null,
    classType: SubjectClassType = SubjectClassType.NONE,
    isLongClicked: Boolean,
    isTotalCountZero: Boolean,
    isArchived: Boolean = false,
    isClassScheduledToday: Boolean = false,
    todayStatus: AttendanceStatus? = null,
    onMarkPresent: () -> Unit = {},
    onMarkAbsent: () -> Unit = {},
    onEditButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onErrorIconClicked: () -> Unit,
) {
    val context = LocalContext.current
    val progressText = "${String.format("%.0f", progress * 100)}%"

    var contentHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surfaceContainer)
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
                    .padding(20.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500, easing = FastOutSlowInEasing
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SubjectText(
                            subject = subject,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (isArchived) {
                            ArchivedIndicator()
                        }
                    }

                    val classTypeText = classTypeToString(
                        context,
                        classType
                    ).takeUnless { classType == SubjectClassType.NONE }

                    val roomText = room?.takeIf { it.isNotBlank() }

                    val infoText = listOfNotNull(
                        roomText,
                        classTypeText
                    ).joinToString(" | ")

                    if (infoText.isNotEmpty()) {
                        Text(
                            text = infoText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }

                if (isLongClicked) {
                    UtilityRow(
                        onEditButtonClicked = onEditButtonClicked,
                        onDeleteButtonClicked = onDeleteButtonClicked
                    )
                } else {
                    if (isClassScheduledToday) {
                        QuickAttendanceButtons(
                            todayStatus = todayStatus,
                            onMarkPresent = onMarkPresent,
                            onMarkAbsent = onMarkAbsent
                        )
                    }

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
fun QuickAttendanceButtons(
    modifier: Modifier = Modifier,
    todayStatus: AttendanceStatus?,
    onMarkPresent: () -> Unit,
    onMarkAbsent: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isPresent = todayStatus == AttendanceStatus.PRESENT
        val isAbsent = todayStatus == AttendanceStatus.ABSENT

        val presentContainerColor by animateColorAsState(
            targetValue = if (isPresent) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
            label = "presentContainerColor"
        )
        val presentContentColor by animateColorAsState(
            targetValue = if (isPresent) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.primary,
            label = "presentContentColor"
        )

        val absentContainerColor by animateColorAsState(
            targetValue = if (isAbsent) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f),
            label = "absentContainerColor"
        )
        val absentContentColor by animateColorAsState(
            targetValue = if (isAbsent) MaterialTheme.colorScheme.onError
            else MaterialTheme.colorScheme.error,
            label = "absentContentColor"
        )

        // Tick button (Present)
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(presentContainerColor)
                .clickable(onClick = onMarkPresent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = stringResource(R.string.present),
                modifier = Modifier.size(18.dp),
                tint = presentContentColor
            )
        }

        // X button (Absent)
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(absentContainerColor)
                .clickable(onClick = onMarkAbsent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = stringResource(R.string.absent),
                modifier = Modifier.size(18.dp),
                tint = absentContentColor
            )
        }
    }
}

@Composable
fun ArchivedIndicator(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Archive,
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = stringResource(R.string.archived),
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
            )
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
