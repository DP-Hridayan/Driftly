package `in`.hridayan.driftly

import android.os.Bundle
import android.widget.Toast
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
import `in`.hridayan.driftly.core.common.LocalDarkMode
import `in`.hridayan.driftly.core.common.LocalSeedColor
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.constants.SeedColorProvider
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel.AutoUpdateViewModel
import kotlinx.coroutines.flow.collect
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
            val autoUpdateEnabled = store.isEnabled(SettingsKeys.AUTO_UPDATE).first()
            if (autoUpdateEnabled) {
                autoUpdateViewModel.checkForUpdates(BuildConfig.VERSION_NAME)
            }

            autoUpdateViewModel.updateEvents.collect { result ->
                when (result) {
                    is UpdateResult.Success -> {
                        if (result.isUpdateAvailable) {
                            Toast.makeText(this@MainActivity, "Update available!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    UpdateResult.NetworkError -> {
                        Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }

                    UpdateResult.Timeout -> {
                        Toast.makeText(this@MainActivity, "Request timeout", Toast.LENGTH_SHORT).show()
                    }

                    UpdateResult.UnknownError -> {
                        Toast.makeText(this@MainActivity, "Unknown error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            CompositionLocals(store) {
                val settings = LocalSettings.current
                val isDarkMode = LocalDarkMode.current
                SeedColorProvider.seedColor = LocalSeedColor.current

                DriftlyTheme(
                    darkTheme = isDarkMode,
                    dynamicColor = settings.isDynamicColor,
                    isHighContrastDarkTheme = settings.isHighContrastDarkMode
                ) {
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


