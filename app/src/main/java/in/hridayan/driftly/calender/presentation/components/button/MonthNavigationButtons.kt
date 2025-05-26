@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.calender.presentation.components.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.tooltip.TooltipContent

@Composable
fun MonthNavigationButtons(
    modifier: Modifier = Modifier,
    onNavigatePrev: () -> Unit,
    onNavigateNext: () -> Unit,
    onReset: () -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TooltipContent(stringResource(R.string.previous_month)) {
            IconButton(
                onClick = {
                    weakHaptic()
                    onNavigatePrev()
                },
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = "Previous"
                )
            }
        }

        TooltipContent(stringResource(R.string.reset_to_current_month)) {
            IconButton(
                onClick = {
                    weakHaptic()
                    onReset()
                },
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Replay,
                    contentDescription = "Reset"
                )
            }
        }

        TooltipContent(stringResource(R.string.next_month)) {
            IconButton(
                onClick = {
                    weakHaptic()
                    onNavigateNext()
                },
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = "Next"
                )
            }
        }
    }
}