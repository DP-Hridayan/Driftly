@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.presentation.components.item.SettingsItemLayout
import `in`.hridayan.driftly.settings.presentation.components.radiobutton.ThemeRadioGroup
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.domain.model.ThemeOption
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel.LookAndFeelViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun DarkThemeScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    lookAndFeelViewModel: LookAndFeelViewModel = hiltViewModel()
) {
    val current by lookAndFeelViewModel.themeOption.collectAsState()
    val options = ThemeOption.entries.map { option ->
        stringResource(option.labelResId) to option
    }
    val highContrastDarkTheme = settingsViewModel.highContrastDarkThemeSetting

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.dark_theme)
    ) { innerPadding, topBarScrollBehavior ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ThemeRadioGroup(
                        options = options,
                        selected = current,
                        onSelectedChange = lookAndFeelViewModel::select,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.additional_settings),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 20.dp, top = 25.dp, bottom = 10.dp)
                )
            }

            item {
                highContrastDarkTheme?.let { (item, isEnabled) ->
                    SettingsItemLayout(
                        item = item,
                        isEnabled = isEnabled,
                        onToggle = { settingsViewModel.onToggle(item.key) },
                        onClick = { clickedItem -> lookAndFeelViewModel.onItemClicked(clickedItem) },
                    )
                }
            }

        }
    }
}