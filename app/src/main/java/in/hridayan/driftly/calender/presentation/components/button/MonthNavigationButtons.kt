@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.calender.presentation.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonDefaults.extraSmallContainerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import `in`.hridayan.driftly.core.common.LocalWeakHaptic

@Composable
fun MonthNavigationButtons(
    modifier: Modifier = Modifier,
    onNavigatePrev: () -> Unit,
    onNavigateNext: () -> Unit,
    onReset: () -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current

    val interactionSources = remember { List(3) { MutableInteractionSource() } }

    @Suppress("DEPRECATION")
    ButtonGroup(modifier = modifier.wrapContentSize()) {

        FilledIconButton(
            onClick = {
                weakHaptic()
                onNavigatePrev()
            },
            shapes = IconButtonDefaults.shapes(),
            interactionSource = interactionSources[0],
            modifier = Modifier
                .size(extraSmallContainerSize())
                .animateWidth(interactionSources[0]),
        ) {
            Icon(
                modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize),
                imageVector = Icons.Rounded.ChevronLeft,
                contentDescription = "Previous"
            )
        }

        FilledIconButton(
            onClick = {
                weakHaptic()
                onReset()
            },
            shapes = IconButtonDefaults.shapes(),
            interactionSource = interactionSources[1],
            modifier = Modifier
                .size(extraSmallContainerSize())
                .animateWidth(interactionSources[1]),
        ) {
            Icon(
                modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize),
                imageVector = Icons.Rounded.Replay,
                contentDescription = "Reset"
            )
        }

        FilledIconButton(
            onClick = {
                weakHaptic()
                onNavigateNext()
            },
            shapes = IconButtonDefaults.shapes(),
            interactionSource = interactionSources[2],
            modifier = Modifier
                .size(extraSmallContainerSize())
                .animateWidth(interactionSources[2]),
        ) {
            Icon(
                modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize),
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
}