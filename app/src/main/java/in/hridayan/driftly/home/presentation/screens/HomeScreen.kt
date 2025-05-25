package `in`.hridayan.driftly.home.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.animateFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.domain.model.TotalAttendance
import `in`.hridayan.driftly.home.presentation.components.card.AttendanceOverviewCard
import `in`.hridayan.driftly.home.presentation.components.card.SubjectCard
import `in`.hridayan.driftly.home.presentation.components.dialog.AddSubjectDialog
import `in`.hridayan.driftly.home.presentation.components.image.UndrawRelaxedReading
import `in`.hridayan.driftly.home.presentation.viewmodel.HomeViewModel
import `in`.hridayan.driftly.navigation.CalendarScreen
import `in`.hridayan.driftly.navigation.LocalNavController
import `in`.hridayan.driftly.navigation.SettingsScreen

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val weakHaptic = LocalWeakHaptic.current
    val navController = LocalNavController.current
    val subjects by viewModel.subjectList.collectAsState(initial = emptyList())
    val subjectCount by viewModel.subjectCount.collectAsState(initial = 0)
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val totalAttendance by viewModel.getTotalAttendanceCounts()
        .collectAsState(initial = TotalAttendance())
    val totalPresent = totalAttendance.totalPresent
    val totalCount = totalAttendance.totalCount

    var showAddButton by rememberSaveable { mutableStateOf(true) }

    val subjectCardCornerRadius = LocalSettings.current.subjectCardCornerRadius

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.Companion
                    .padding(bottom = 10.dp)
                    .animateFloatingActionButton(
                        visible = showAddButton,
                        alignment = Alignment.Center,
                        targetScale = 0f,
                        scaleAnimationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                        alphaAnimationSpec = tween(durationMillis = 150),
                    ),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    if (!showAddButton) return@FloatingActionButton
                    isDialogOpen = true
                    weakHaptic()
                },
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
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(), bottom = 25.dp, start = 25.dp, end = 25.dp
            ),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 35.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.alpha(0.95f)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(R.drawable.ic_settings),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                navController.navigate(SettingsScreen)
                                weakHaptic()
                            })
                    )
                }
            }

            if (subjectCount == 0 || totalCount == 0) {
                item {
                    Box(
                        modifier
                            .fillMaxWidth()
                            .then(
                                if (subjectCount == 0) Modifier.height(400.dp)
                                else Modifier.padding(vertical = 20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        UndrawRelaxedReading()
                    }
                }
            }

            if (subjectCount == 0) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.75f),
                        text = stringResource(R.string.no_subject_yet),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (subjectCount != 0 && totalCount != 0) {
                item {
                    AttendanceOverviewCard(
                        modifier = Modifier.padding(
                            top = 15.dp,
                            bottom = 10.dp
                        ),
                        presentCount = totalPresent,
                        totalCount = totalCount
                    )
                }
            }

            items(subjects.size, key = { index -> subjects[index].id }) { index ->

                val counts by viewModel.getSubjectAttendanceCounts(subjects[index].id)
                    .collectAsState(initial = SubjectAttendance())

                val progress = counts.presentCount.toFloat() / counts.totalCount.toFloat()

                SubjectCard(
                    cardStyle = LocalSettings.current.subjectCardStyle,
                    cornerRadius = subjectCardCornerRadius.dp,
                    subjectId = subjects[index].id,
                    subject = subjects[index].subject,
                    progress = progress,
                    isTotalCountZero = counts.totalCount == 0,
                    navigate = {
                        navController.navigate(
                            CalendarScreen(
                                subjectId = subjects[index].id, subject = subjects[index].subject
                            )
                        )
                    },
                    onLongClicked = { isLongClicked ->
                        showAddButton = !isLongClicked
                    },
                    onDeleteConfirmed = { showAddButton = true },
                    onUpdateConfirmed = { showAddButton = true })
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
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
