@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.presentation.page.behavior.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.presentation.components.scaffold.SettingsScaffold

@Composable
fun BehaviorScreen(modifier: Modifier = Modifier) {
    SettingsScaffold(modifier = modifier, topBarTitle = stringResource(R.string.behavior)) {
        innerPadding , topBarScrollBehavior ->
    }
}