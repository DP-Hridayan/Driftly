package `in`.hridayan.driftly.settings.data.local

import androidx.appcompat.app.AppCompatDelegate
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.common.constants.SeedColorProvider
import `in`.hridayan.driftly.core.common.constants.SubjectCardStyle

enum class SettingsKeys(val default: Any?) {
    LOOK_AND_FEEL(null),
    AUTO_UPDATE(false),
    ABOUT(null),
    BEHAVIOR(null),
    STREAK_MODIFIER(true),
    LANGUAGE(null),
    THEME_MODE(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    DARK_THEME(null),
    HIGH_CONTRAST_DARK_MODE(false),
    SEED_COLOR(SeedColorProvider.seedColor),
    DYNAMIC_COLORS(true),
    HAPTICS_AND_VIBRATION(true),
    VERSION(null),
    CHANGELOGS(null),
    REPORT(null),
    FEATURE_REQUEST(null),
    GITHUB(null),
    LICENSE(null),
    CUSTOMISATION(null),
    SUBJECT_CARD_CORNER_RADIUS(8f),
    SUBJECT_CARD_STYLE(SubjectCardStyle.CARD_STYLE_A),
    GITHUB_RELEASE_TYPE(GithubReleaseType.STABLE),
    SAVED_VERSION_CODE(0),
    REMEMBER_CALENDAR_MONTH_YEAR(false)
}