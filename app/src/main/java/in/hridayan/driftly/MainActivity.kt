package `in`.hridayan.driftly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import `in`.hridayan.driftly.core.common.CompositionLocals
import `in`.hridayan.driftly.core.common.LocalSeedColor
import `in`.hridayan.driftly.core.domain.provider.SeedColorProvider
import `in`.hridayan.driftly.core.domain.model.GithubReleaseType
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.theme.DriftlyTheme
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel.AutoUpdateViewModel
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val autoUpdateViewModel: AutoUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val autoUpdateEnabled = settingsViewModel.getBoolean(SettingsKeys.AUTO_UPDATE).first()
            val includePrerelease =
                settingsViewModel.getInt(SettingsKeys.GITHUB_RELEASE_TYPE)
                    .first() == GithubReleaseType.PRE_RELEASE

            if (autoUpdateEnabled) {
                autoUpdateViewModel.checkForUpdates(
                    includePrerelease = includePrerelease
                )
            }
        }

        enableEdgeToEdge()
        setContent {
            CompositionLocals {
                SeedColorProvider.setSeedColor(LocalSeedColor.current)

                DriftlyTheme {
                    Surface(
                        modifier = Modifier.Companion.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        AppEntry()
                    }
                }
            }
        }
    }
}


