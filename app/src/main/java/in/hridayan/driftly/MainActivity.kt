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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import `in`.hridayan.driftly.core.presentation.AppEntry
import `in`.hridayan.driftly.core.presentation.ui.theme.DriftlyTheme
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.settings.data.SettingsDataStore
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var store: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val mode by store.intFlow(
                SettingsKeys.THEME_MODE, default = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            ).collectAsState(initial = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            val isDarkTheme = when (mode) {
                AppCompatDelegate.MODE_NIGHT_YES -> true
                AppCompatDelegate.MODE_NIGHT_NO -> false
                else -> isSystemInDarkTheme()
            }

            val isHighContrastDarkTheme by store.isEnabled(SettingsKeys.HIGH_CONTRAST_DARK_MODE)
                .collectAsState(initial = false)

            DriftlyTheme(
                darkTheme = isDarkTheme,
                isHighContrastDarkTheme = isHighContrastDarkTheme
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


