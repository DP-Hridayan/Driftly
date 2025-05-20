package `in`.hridayan.driftly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import `in`.hridayan.driftly.core.common.CompositionLocals
import `in`.hridayan.driftly.core.common.LocalDarkMode
import `in`.hridayan.driftly.core.common.LocalSeedColor
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.constants.SeedColorProvider
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var store: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


