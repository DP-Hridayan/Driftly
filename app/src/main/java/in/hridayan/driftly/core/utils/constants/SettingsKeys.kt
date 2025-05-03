package `in`.hridayan.driftly.core.utils.constants

import androidx.appcompat.app.AppCompatDelegate

enum class SettingsKeys(val default: Any?) {
    LOOK_AND_FEEL(null),
    AUTO_UPDATE(false),
    ABOUT(null),
    LANGUAGE(null),
    THEME_MODE(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    HIGH_CONTRAST_DARK_MODE(false),
    DYNAMIC_COLORS(true),
    HAPTICS_AND_VIBRATION(true)
}