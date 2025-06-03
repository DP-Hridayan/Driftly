@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.home.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.common.constants.SubjectCardStyle
import `in`.hridayan.driftly.core.presentation.components.dialog.ConfirmDeleteDialog
import `in`.hridayan.driftly.home.presentation.components.dialog.EditSubjectDialog
import `in`.hridayan.driftly.home.presentation.components.dialog.NoAttendanceDialog
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectId: Int,
    subject: String,
    progress: Float,
    isTotalCountZero: Boolean = false,
    selectedCardsCount: Int = 0,
    navigate: () -> Unit = {},
    onClick: () -> Unit = {},
    onLongClicked: (Boolean) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    isDemoCard: Boolean = false,
    cornerRadius: Dp = 15.dp,
    cardStyle: Int = SubjectCardStyle.CARD_STYLE_A,
) {
    val weakHaptic = LocalWeakHaptic.current
    var isLongClicked by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isUpdateDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isNoAttendanceDialogVisible by rememberSaveable { mutableStateOf(false) }

    val handleLongClick = {
        if (!isDemoCard) {
            isLongClicked = !isLongClicked
            onLongClicked(isLongClicked)
            weakHaptic()
        }
    }

    val handleClick = {
        onClick()
        if (isLongClicked || selectedCardsCount != 0) {
            handleLongClick()
        } else {
            navigate()
        }
        weakHaptic()
    }

    val handleDeleteConfirmation = {
        viewModel.deleteSubject(subjectId, onSuccess = {
            viewModel.deleteAllAttendanceForSubject(subjectId)
            onLongClicked(false)
            isDeleteDialogVisible = false
        })
    }

    val onEditButtonClicked: () -> Unit = {
        weakHaptic()
        isUpdateDialogVisible = true
    }

    val onDeleteButtonClicked: () -> Unit = {
        weakHaptic()
        isDeleteDialogVisible = true
    }

    val onErrorIconClicked: () -> Unit = {
        weakHaptic()
        isNoAttendanceDialogVisible = true
    }

    if (selectedCardsCount == 0 && isLongClicked) isLongClicked = false

    BaseCard(
        modifier = modifier,
        cornerRadius = cornerRadius,
        onClick = handleClick,
        onLongClick = handleLongClick,
    ) {
        when (cardStyle) {
            SubjectCardStyle.CARD_STYLE_A ->
                CardStyleA(
                    subject = subject,
                    isLongClicked = isLongClicked,
                    isTotalCountZero = isTotalCountZero,
                    progress = progress,
                    onEditButtonClicked = onEditButtonClicked,
                    onDeleteButtonClicked = onDeleteButtonClicked,
                    onErrorIconClicked = onErrorIconClicked
                )

            SubjectCardStyle.CARD_STYLE_B ->
                CardStyleB(
                    subject = subject,
                    isLongClicked = isLongClicked,
                    isTotalCountZero = isTotalCountZero,
                    progress = progress,
                    onEditButtonClicked = onEditButtonClicked,
                    onDeleteButtonClicked = onDeleteButtonClicked,
                    onErrorIconClicked = onErrorIconClicked
                )
        }
    }

    if (isDeleteDialogVisible) {
        ConfirmDeleteDialog(
            onDismiss = {
                isDeleteDialogVisible = false
            },
            onConfirm = handleDeleteConfirmation
        )
    }

    if (isUpdateDialogVisible) {
        EditSubjectDialog(
            subjectId = subjectId,
            subject = subject,
            onDismiss = {
                isLongClicked = false
                onLongClicked(false)
                isUpdateDialogVisible = false
            })
    }

    if (isNoAttendanceDialogVisible) {
        NoAttendanceDialog(onDismiss = {
            isNoAttendanceDialogVisible = false
        })
    }
}

@Composable
fun ErrorIcon(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = Icons.Rounded.ErrorOutline,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
        contentDescription = null,
        modifier = modifier
            .padding(end = 2.dp)
            .size(36.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    )
}

@Composable
fun UtilityRow(
    modifier: Modifier = Modifier,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onEditButtonClicked,
            shapes = IconButtonDefaults.shapes(),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
            )
        }

        IconButton(
            onClick = onDeleteButtonClicked,
            shapes = IconButtonDefaults.shapes(),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
            )
        }
    }
}
