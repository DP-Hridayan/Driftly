@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.screens

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalWeakHaptic
import `in`.hridayan.driftly.core.presentation.components.svg.DynamicColorImageVectors
import `in`.hridayan.driftly.core.presentation.components.svg.vectors.themePicker
import `in`.hridayan.driftly.core.utils.MiUiCheck
import `in`.hridayan.driftly.navigation.LocalNavController
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.presentation.components.item.SettingsItemLayout
import `in`.hridayan.driftly.settings.presentation.components.tab.ColorTabs
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel.LookAndFeelViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun LookAndFeelScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    lookAndFeelViewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val weakHaptic = LocalWeakHaptic.current
    val navController = LocalNavController.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val settings = settingsViewModel.lookAndFeelPageList
    val dynamicColorSetting = settingsViewModel.dynamicColorSetting
    val context = LocalContext.current
    val isMiUi = MiUiCheck.isMiui
    val isSdkLowerThan13 = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    LaunchedEffect(Unit) {
        lookAndFeelViewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.LaunchIntent -> {
                    context.startActivity(event.intent)
                }

                is SettingsUiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                else -> {}
            }
        }

        settingsViewModel.loadSettings()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    val collapsedFraction = scrollBehavior.state.collapsedFraction

                    val expandedFontSize = 33.sp
                    val collapsedFontSize = 20.sp

                    val fontSize = lerp(expandedFontSize, collapsedFontSize, collapsedFraction)
                    Text(
                        text = stringResource(R.string.look_and_feel),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize,
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 0.05.em
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        weakHaptic()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ) {
            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp, vertical = 25.dp),
                    imageVector = DynamicColorImageVectors.themePicker(),
                    contentDescription = null
                )
            }

            item {
                ColorTabs(modifier = Modifier.padding(20.dp))
            }

            item {
                dynamicColorSetting?.let { (item, isEnabled) ->
                    SettingsItemLayout(
                        item = item,
                        isEnabled = isEnabled,
                        onToggle = { settingsViewModel.onToggle(item.key) },
                        onClick = { clickedItem -> lookAndFeelViewModel.onItemClicked(clickedItem) },
                    )
                }
            }


            item {
                Text(
                    text = stringResource(R.string.additional_settings),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                )
            }

            itemsIndexed(
                items = settings
            ) { _, pair ->
                val (item, isEnabled) = pair
                SettingsItemLayout(
                    item = item,
                    isEnabled = isEnabled,
                    isLayoutVisible = !(item.key == SettingsKeys.LANGUAGE && (isSdkLowerThan13 || isMiUi)),
                    onToggle = { settingsViewModel.onToggle(item.key) },
                    onClick = { clickedItem -> lookAndFeelViewModel.onItemClicked(clickedItem) },
                )
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
}