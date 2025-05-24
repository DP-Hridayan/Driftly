@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.behavior.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.presentation.components.item.SettingsItemLayout
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun BehaviorScreen(modifier: Modifier = Modifier,
                   settingsViewModel: SettingsViewModel = hiltViewModel(),) {
    val settings = settingsViewModel.behaviorPageList

    SettingsScaffold(modifier = modifier, topBarTitle = stringResource(R.string.behavior)) {
        innerPadding , topBarScrollBehavior ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ){
            itemsIndexed(
                items = settings
            ) { _, pair ->
                val (item, isEnabled) = pair
                SettingsItemLayout(
                    item = item,
                    isEnabled = isEnabled,
                    onToggle = { settingsViewModel.onToggle(item.key) },
                    onClick = { clickedItem -> settingsViewModel.onItemClicked(clickedItem) },
                )
            }
        }
    }
}