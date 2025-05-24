package `in`.hridayan.driftly.settings.domain.model

data class SettingsState(
    val isAutoUpdate: Boolean,
    val themeMode: Int,
    val isHighContrastDarkMode: Boolean,
    val seedColor : Int,
    val isDynamicColor: Boolean,
    val isHapticEnabled: Boolean,
    val subjectCardCornerRadius: Float,
    val subjectCardStyle: Int,
    val githubReleaseType: Int,
    val savedVersionCode: Int,
    val showAttendanceStreaks: Boolean,
    val rememberCalendarMonthYear: Boolean
)