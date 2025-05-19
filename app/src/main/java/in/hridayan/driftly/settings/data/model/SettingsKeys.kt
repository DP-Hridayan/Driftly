package `in`.hridayan.driftly.settings.data.model

import androidx.appcompat.app.AppCompatDelegate
import `in`.hridayan.driftly.core.common.constants.SeedColors

enum class SettingsKeys(val default: Any?) {
    LOOK_AND_FEEL(null),
    AUTO_UPDATE(false),
    ABOUT(null),
    LANGUAGE(null),
    THEME_MODE(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    HIGH_CONTRAST_DARK_MODE(false),
    SEED_COLOR(SeedColors.RED),
    DYNAMIC_COLORS(true),
    HAPTICS_AND_VIBRATION(true),
    VERSION(null),
    CHANGELOGS(null),
    REPORT(null),
    FEATURE_REQUEST(null),
    GITHUB(null),
    LICENSE(null),
}