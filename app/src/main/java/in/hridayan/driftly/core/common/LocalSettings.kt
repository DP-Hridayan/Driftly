package `in`.hridayan.driftly.core.common

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.compositionLocalOf
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.common.constants.SeedColors
import `in`.hridayan.driftly.core.common.constants.SubjectCardStyle
import `in`.hridayan.driftly.settings.domain.model.SettingsState

val LocalSettings = compositionLocalOf<SettingsState> {
    SettingsState(
        isAutoUpdate = false,
        themeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        isHighContrastDarkMode = false,
        seedColor = SeedColors.Blue.seed,
        isDynamicColor = true,
        isHapticEnabled = true,
        subjectCardCornerRadius = 8f,
        subjectCardStyle = SubjectCardStyle.CARD_STYLE_A,
        githubReleaseType = GithubReleaseType.STABLE,
        savedVersionCode = 0,
        showAttendanceStreaks = true,
        rememberCalendarMonthYear = false,
        startWeekOnMonday = true,
        enableDirectDownload = true,
        notificationPreference = true,
        notificationPermissionDialogShown = false
    )
}