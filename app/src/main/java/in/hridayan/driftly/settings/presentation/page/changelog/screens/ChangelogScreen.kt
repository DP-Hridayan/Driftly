@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.changelog.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.presentation.components.item.ChangelogItemLayout
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold
import `in`.hridayan.driftly.settings.presentation.page.changelog.viewmodel.ChangelogViewModel

@Composable
fun ChangelogScreen(
    modifier: Modifier = Modifier,
    changelogViewModel: ChangelogViewModel = hiltViewModel()
) {
    val changelogs = changelogViewModel.changelogs.value

    SettingsScaffold(
        modifier = modifier,
        topBarTitle = stringResource(R.string.changelogs)
    ) { innerPadding, topBarScrollBehavior ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
            contentPadding = innerPadding
        ) {
            itemsIndexed(items = changelogs) { index, item ->
                ChangelogItemLayout(
                    modifier = Modifier.padding(25.dp),
                    versionName = item.versionName,
                    changelog = changelogViewModel.splitStringToLines(item.changelog)
                )

                if (index != changelogs.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp)
                            .alpha(0.5f),
                        color = MaterialTheme.colorScheme.onSurface,
                        thickness = 1.dp
                    )
                }
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
