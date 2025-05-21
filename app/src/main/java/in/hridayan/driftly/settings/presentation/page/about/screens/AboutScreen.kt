@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.about.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.utils.openUrl
import `in`.hridayan.driftly.navigation.LocalNavController
import `in`.hridayan.driftly.settings.presentation.components.card.SupportMeCard
import `in`.hridayan.driftly.settings.presentation.components.image.ProfilePicCircular
import `in`.hridayan.driftly.settings.presentation.components.item.SettingsItemLayout
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.page.about.viewmodel.AboutViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    aboutViewModel: AboutViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val settings = aboutViewModel.aboutPageList

    LaunchedEffect(Unit) {
        aboutViewModel.uiEvent.collect { event ->
            when (event) {
                is SettingsUiEvent.Navigate -> {
                    navController.navigate(event.route)
                }

                is SettingsUiEvent.OpenUrl -> {
                    openUrl(event.url, context)
                }

                else -> {}
            }
        }
    }

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.about)
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
                Text(
                    text = stringResource(R.string.lead_developer),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    ProfilePicCircular(
                        painter = painterResource(R.mipmap.dp_hridayan),
                        size = 150.dp,
                    )

                    Text(
                        text = "Hridayan",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = stringResource(R.string.des_hridayan),
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                    )

                    SupportMeCard(modifier = modifier.padding(horizontal = 20.dp))
                }
            }

            item {
                Text(
                    text = stringResource(R.string.app),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 20.dp, top = 45.dp, bottom = 25.dp)
                )
            }

            itemsIndexed(
                items = settings
            ) { _, pair ->
                val (item, isEnabled) = pair
                SettingsItemLayout(
                    item = item,
                    isEnabled = isEnabled,
                    onToggle = { settingsViewModel.onToggle(item.key) },
                    onClick = { clickedItem -> aboutViewModel.onItemClicked(clickedItem) },
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