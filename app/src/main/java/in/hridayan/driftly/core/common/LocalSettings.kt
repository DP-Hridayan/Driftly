package `in`.hridayan.driftly.core.common

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.compositionLocalOf
import `in`.hridayan.driftly.core.domain.model.GithubReleaseType
import `in`.hridayan.driftly.core.domain.model.SubjectCardStyle
import `in`.hridayan.driftly.core.domain.provider.SeedColorProvider
import `in`.hridayan.driftly.settings.domain.model.CustomFontFamily
import `in`.hridayan.driftly.settings.domain.model.SettingsState

val LocalSettings = compositionLocalOf {
    SettingsState(
        isAutoUpdate = false,
        themeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        isHighContrastDarkMode = false,
        seedColor = SeedColorProvider.seed,
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
        notificationPermissionDialogShown = false,
        showGithubWarningDialog = true,
        fontFamily = CustomFontFamily.SYSTEM_FONT
    )
}