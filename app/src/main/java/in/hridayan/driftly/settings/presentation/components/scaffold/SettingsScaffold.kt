@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.settings.presentation.components.scaffold

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.lerp
import `in`.hridayan.driftly.core.presentation.components.button.BackButton

@Composable
fun SettingsScaffold(
    modifier: Modifier = Modifier,
    topBarTitle: String,
    listState: LazyListState,
    content: @Composable (innerPadding: PaddingValues, topBarScrollBehavior: TopAppBarScrollBehavior) -> Unit,
    fabContent: @Composable (expanded: Boolean) -> Unit = {}
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val expanded by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset < 10
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    val collapsedFraction = scrollBehavior.state.collapsedFraction
                    val expandedFontSize = MaterialTheme.typography.displaySmallEmphasized.fontSize
                    val collapsedFontSize = MaterialTheme.typography.headlineSmall.fontSize

                    val fontSize = lerp(expandedFontSize, collapsedFontSize, collapsedFraction)

                    Text(
                        modifier = modifier.basicMarquee(),
                        text = topBarTitle,
                        maxLines = 1,
                        fontSize = fontSize,
                        style = MaterialTheme.typography.displaySmallEmphasized
                    )
                },
                navigationIcon = { BackButton() },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            fabContent(expanded)
        }) { innerPadding ->
        content(innerPadding, scrollBehavior)
    }
}