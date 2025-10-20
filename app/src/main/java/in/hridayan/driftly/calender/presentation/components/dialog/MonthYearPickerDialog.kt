@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.calender.presentation.components.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.text.AutoResizeableText
import java.util.Calendar

@SuppressLint("LocalContextResourcesRead")
@Composable
fun MonthYearPickerDialog(
    yearDisplayed: Int, monthDisplayed: Int, onDismiss: () -> Unit, onConfirm: (Int, Int) -> Unit
) {
    val weakHaptic = LocalWeakHaptic.current
    val context = LocalContext.current

    val months: List<String> = context.resources.getStringArray(R.array.month_names).toList()

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear - 10..currentYear + 10).toList()

    var expandedMonth by remember { mutableStateOf(false) }
    var expandedYear by remember { mutableStateOf(false) }

    var selectedMonth by remember { mutableIntStateOf(monthDisplayed - 1) }
    var selectedYear by remember { mutableIntStateOf(yearDisplayed) }

    val interactionSources = remember { List(2) { MutableInteractionSource() } }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AutoResizeableText(
                text = stringResource(R.string.select_month_and_year),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Month dropdown
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val dropdownItemHeight = 48.dp
                val maxVisibleItems = 6
                val maxHeight = dropdownItemHeight * maxVisibleItems

                ExposedDropdownMenuBox(
                    expanded = expandedMonth,
                    onExpandedChange = { expandedMonth = !expandedMonth },
                ) {
                    OutlinedTextField(
                        value = months[selectedMonth],
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        label = { Text(stringResource(R.string.month)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMonth)
                        },
                        modifier = Modifier.menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandedMonth,
                        onDismissRequest = { expandedMonth = false },
                        modifier = Modifier
                            .heightIn(max = maxHeight)
                            .verticalScroll(rememberScrollState())
                    ) {
                        months.forEach { month ->
                            DropdownMenuItem(
                                modifier = Modifier,
                                text = {
                                    Text(
                                        text = month,
                                        maxLines = 1,
                                        autoSize = TextAutoSize.StepBased(
                                            minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                                            maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                            stepSize = 0.5.sp
                                        )
                                    )
                                },
                                onClick = {
                                    weakHaptic()
                                    selectedMonth = months.indexOf(month)
                                    expandedMonth = false
                                })
                        }
                    }
                }

                // Year Dropdown
                ExposedDropdownMenuBox(
                    expanded = expandedYear,
                    onExpandedChange = { expandedYear = !expandedYear },
                ) {
                    OutlinedTextField(
                        value = selectedYear.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.year)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear)
                        },
                        modifier = Modifier.menuAnchor(
                            ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true
                        )
                    )

                    val scrollState = rememberScrollState()
                    val itemHeightPx = with(LocalDensity.current) { dropdownItemHeight.toPx() }

                    LaunchedEffect(expandedYear) {
                        if (expandedYear) {
                            scrollState.scrollTo((10 * itemHeightPx).toInt())
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false },
                        modifier = Modifier
                            .heightIn(max = maxHeight)
                            .verticalScroll(scrollState)
                    ) {
                        years.forEach { year ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = year.toString(),
                                        maxLines = 1,
                                        autoSize = TextAutoSize.StepBased(
                                            minFontSize = MaterialTheme.typography.bodySmall.fontSize,
                                            maxFontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                            stepSize = 0.5.sp
                                        )
                                    )
                                },
                                onClick = {
                                    weakHaptic()
                                    selectedYear = year
                                    expandedYear = false
                                })
                        }
                    }
                }
            }

            @Suppress("DEPRECATION")
            ButtonGroup(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = {
                        weakHaptic()
                        onDismiss()
                    },
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier
                        .weight(1f)
                        .animateWidth(interactionSources[0]),
                    interactionSource = interactionSources[0],
                ) {
                    AutoResizeableText(text = stringResource(R.string.cancel))
                }

                Button(
                    onClick = {
                        weakHaptic()
                        onConfirm(selectedMonth + 1, selectedYear)
                        onDismiss()
                    },
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier
                        .weight(1f)
                        .animateWidth(interactionSources[1]),
                    interactionSource = interactionSources[1],
                ) {
                    AutoResizeableText(text = stringResource(R.string.select))
                }
            }
        }
    }
}
