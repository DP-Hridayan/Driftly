package `in`.hridayan.driftly.home.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.presentation.components.progress.AnimatedHalfCircleProgress
import `in`.hridayan.driftly.home.components.card.SubjectCard
import `in`.hridayan.driftly.home.components.dialog.AddSubjectDialog
import `in`.hridayan.driftly.home.components.label.Label
import `in`.hridayan.driftly.home.presentation.viewmodel.AttendanceCounts
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewmodel

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewmodel = hiltViewModel(),
) {
    val subjects by viewModel.subjectList.collectAsState(initial = emptyList())
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val attendanceSummary by viewModel.attendanceSummary.collectAsState()
    val totalPresent = attendanceSummary.totalPresent
    val totalAbsent = attendanceSummary.totalAbsent
    val totalCount = attendanceSummary.totalCount
    val totalProgress = totalPresent.toFloat() / totalCount.toFloat()
    val totalProgressText = "${String.format("%.0f", totalProgress * 100)}%"

    val progressColor = lerp(
        start = MaterialTheme.colorScheme.error,
        stop = MaterialTheme.colorScheme.primary,
        fraction = totalProgress.coerceIn(0f, 1f)
    )

    LaunchedEffect(Unit) {
        viewModel.calculateAttendanceSummary()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.Companion,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = { isDialogOpen = true },
            ) {
                val rotationAngle by animateFloatAsState(if (isDialogOpen) 45f else 0f)
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = 25.dp,
                start = 25.dp,
                end = 25.dp
            ),
        ) {
            item {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 45.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    AnimatedHalfCircleProgress(
                        progress = totalProgress,
                        animationDuration = 3000,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(100.dp)
                            .width(200.dp)
                    )

                    Text(
                        text = totalProgressText,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        color = progressColor
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Label(
                        text = "${stringResource(R.string.present)} : $totalPresent",
                        labelColor = MaterialTheme.colorScheme.primaryContainer,
                        strokeColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Label(
                        text = "${stringResource(R.string.absent)} : $totalAbsent",
                        labelColor = MaterialTheme.colorScheme.errorContainer,
                        strokeColor = MaterialTheme.colorScheme.onErrorContainer
                    )

                    Label(
                        text = "${stringResource(R.string.total)} : $totalCount",
                        labelColor = MaterialTheme.colorScheme.tertiaryContainer,
                        strokeColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            items(subjects.size, key = { index -> subjects[index].id }) { index ->

                val counts by viewModel.getAttendanceCounts(subjects[index].id)
                    .collectAsState(initial = AttendanceCounts())

                val progress = if (counts.totalCount != 0) {
                    counts.presentCount.toFloat() / counts.totalCount.toFloat()
                } else {
                    0f
                }

                SubjectCard(
                    subjectId = subjects[index].id,
                    subject = subjects[index].subject,
                    progress = progress,
                )
            }
        }
    }

    if (isDialogOpen) {
        AddSubjectDialog(
            onDismiss = {
                isDialogOpen = false
            })
    }
}
