@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.calender.presentation.components.canvas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText

@Composable
fun MonthYearPicker(
    modifier: Modifier = Modifier, fullMonthName: String, year: Int, onClick: () -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current
    Row(
        modifier = modifier.clickable(
            onClick = {
                weakHaptic()
                onClick()
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        ), verticalAlignment = Alignment.CenterVertically
    ) {
        AutoResizeableText(
            text = "$fullMonthName $year", style = MaterialTheme.typography.titleSmall
        )

        IconButton(
            onClick = {
                weakHaptic()
                onClick()
            },
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = "Dropdown icon"
            )
        }
    }
}