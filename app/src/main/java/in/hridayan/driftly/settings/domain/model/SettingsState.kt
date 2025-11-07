package `in`.hridayan.driftly.settings.domain.model

import `in`.hridayan.driftly.core.presentation.provider.SeedColor

data class SettingsState(
    val isAutoUpdate: Boolean,
    val themeMode: Int,
    val isHighContrastDarkMode: Boolean,
    val seedColor: SeedColor,
    val isDynamicColor: Boolean,
    val isHapticEnabled: Boolean,
    val subjectCardCornerRadius: Float,
    val subjectCardStyle: Int,
    val githubReleaseType: Int,
    val savedVersionCode: Int,
    val showAttendanceStreaks: Boolean,
    val rememberCalendarMonthYear: Boolean,
    val startWeekOnMonday: Boolean,
    val enableDirectDownload: Boolean,
    val notificationPreference: Boolean,
    val notificationPermissionDialogShown: Boolean,
    val showGithubWarningDialog: Boolean,
    val fontFamily: Int
)