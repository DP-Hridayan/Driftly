package `in`.hridayan.driftly.home.presentation.components.card

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.presentation.components.dialog.ConfirmDeleteDialog
import `in`.hridayan.driftly.core.presentation.components.progress.AnimatedCircularProgressIndicator
import `in`.hridayan.driftly.core.presentation.ui.theme.Shape
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.home.presentation.components.dialog.EditSubjectDialog
import `in`.hridayan.driftly.home.presentation.components.dialog.NoAttendanceDialog
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel

@SuppressLint("DefaultLocale")
@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectId: Int,
    subject: String,
    progress: Float,
    isTotalCountZero: Boolean = false,
    navigate: () -> Unit = {},
    onLongClicked: (Boolean) -> Unit = {},
    onDeleteConfirmed: () -> Unit = {},
    onUpdateConfirmed: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val progressText = "${String.format("%.0f", progress * 100)}%"

    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = progress.coerceIn(0f, 1f)
    )

    val weakHaptic = LocalWeakHaptic.current
    var isLongClicked by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isUpdateDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isNoAttendanceDialogVisible by rememberSaveable { mutableStateOf(false) }

    val handleLongClick = {
        isLongClicked = !isLongClicked
        onLongClicked(isLongClicked)
        weakHaptic()

    }

    val handleClick = {
        if (isLongClicked) {
            handleLongClick()
        } else {
            navigate()
        }
        weakHaptic()
    }

    val handleDeleteConfirmation = {
        viewModel.deleteSubject(subjectId, onSuccess = {
            viewModel.deleteAllAttendanceForSubject(subjectId)
            isLongClicked = false
            onLongClicked(isLongClicked)
            isDeleteDialogVisible = false
            onDeleteConfirmed()
        })
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(Shape.cardCornerSmall)
            .combinedClickable(
                enabled = true,
                onClick = { handleClick() },
                onLongClick = { handleLongClick() }),
        shape = Shape.cardCornerSmall,
        colors = CardDefaults.cardColors(
            containerColor = if (isLongClicked) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Row(
            modifier = Modifier
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
            Text(
                text = subject,
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 500, easing = FastOutSlowInEasing
                        )
                    ),
                style = MaterialTheme.typography.titleMedium,
                color = if (isLongClicked) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (isLongClicked) {
                Row(
                    modifier = Modifier.padding(end = 7.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_edit),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isUpdateDialogVisible = true
                                weakHaptic()
                            })
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_delete),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isDeleteDialogVisible = true
                                weakHaptic()
                            })
                    )
                }
            } else {
                if (isTotalCountZero) {
                    Image(
                        painter = painterResource(R.drawable.ic_error),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(36.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    isNoAttendanceDialogVisible = true
                                    weakHaptic()
                                })
                    )
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        AnimatedCircularProgressIndicator(
                            progress = progress, animationDuration = 3000
                        )

                        Text(
                            text = progressText,
                            color = progressColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

            }
        }
    }

    if (isDeleteDialogVisible) {
        ConfirmDeleteDialog(onDismiss = {
            isDeleteDialogVisible = false
        }, onConfirm = {
            handleDeleteConfirmation()
        })
    }

    if (isUpdateDialogVisible) {
        EditSubjectDialog(
            subjectId = subjectId,
            subject = subject,
            onDismiss = {
                isLongClicked = false
                isUpdateDialogVisible = false
                onUpdateConfirmed()
            })
    }

    if (isNoAttendanceDialogVisible) {
        NoAttendanceDialog(onDismiss = {
            isNoAttendanceDialogVisible = false
        })
    }
}