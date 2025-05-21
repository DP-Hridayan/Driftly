package `in`.hridayan.driftly.core.presentation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.core.presentation.components.bottomsheet.UpdateBottomSheet
import `in`.hridayan.driftly.navigation.Navigation
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel.AutoUpdateViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppEntry() {
    val autoUpdateViewModel: AutoUpdateViewModel = hiltViewModel()

    var showUpdateSheet by rememberSaveable { mutableStateOf(false) }
    var tagName by rememberSaveable { mutableStateOf(BuildConfig.VERSION_NAME) }

    LaunchedEffect(Unit) {
        autoUpdateViewModel.updateEvents.collectLatest { result ->
            if (result is UpdateResult.Success && result.isUpdateAvailable) {
                tagName = result.release.tagName
                showUpdateSheet = true
            }
        }
    }

    Surface {
        Navigation()
        if (showUpdateSheet) {
            UpdateBottomSheet(
                onDismiss = { showUpdateSheet = false },
                latestVersion = tagName
            )
        }
    }
}
