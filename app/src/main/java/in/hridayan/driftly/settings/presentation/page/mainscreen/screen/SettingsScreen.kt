@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.mainscreen.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.navigation.LocalNavController
import `in`.hridayan.driftly.settings.presentation.components.item.SettingsItemLayout
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    val navController = LocalNavController.current
    val settings = viewModel.settingsPageList

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                else -> {}
            }
        }

        viewModel.loadSettings()
    }

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.settings)
    ) { innerPadding, topBarScrollBehavior ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ) {
            itemsIndexed(
                items = settings
            ) { _, pair ->
                val (item, isEnabled) = pair
                SettingsItemLayout(
                    item = item,
                    isEnabled = isEnabled,
                    onToggle = { viewModel.onToggle(item.key) },
                    onClick = { clickedItem -> viewModel.onItemClicked(clickedItem) },
                )
            }
        }
    }
}