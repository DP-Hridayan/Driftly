package `in`.hridayan.driftly.core.common

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.compositionLocalOf
import `in`.hridayan.driftly.settings.domain.model.SettingsState

val LocalSettings = compositionLocalOf<SettingsState> {
    SettingsState(
        isAutoUpdate = false,
        isDarkMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        isHighContrastDarkMode = false,
        isDynamicColor = true,
        isHapticEnabled = true
    )
}