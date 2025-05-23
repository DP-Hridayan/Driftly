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
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.common.constants.SeedColorProvider
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel.AutoUpdateViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var store: SettingsDataStore


    private val autoUpdateViewModel: AutoUpdateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val autoUpdateEnabled = store.booleanFlow(SettingsKeys.AUTO_UPDATE).first()
            val includePrerelease =
                store.intFlow(SettingsKeys.GITHUB_RELEASE_TYPE)
                    .first() == GithubReleaseType.PRE_RELEASE

            if (autoUpdateEnabled) {
                autoUpdateViewModel.checkForUpdates(
                    currentVersion = BuildConfig.VERSION_NAME,
                    includePrerelease = includePrerelease
                )
            }
        }

        enableEdgeToEdge()
        setContent {
            CompositionLocals(store) {
                SeedColorProvider.seedColor = LocalSeedColor.current

                DriftlyTheme{
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


