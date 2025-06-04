package `in`.hridayan.driftly.core.common

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import `in`.hridayan.driftly.core.common.constants.SeedColors
import `in`.hridayan.driftly.core.utils.HapticUtils.strongHaptic
import `in`.hridayan.driftly.core.utils.HapticUtils.weakHaptic
import `in`.hridayan.driftly.notification.isNotificationPermissionGranted
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsState
import `in`.hridayan.driftly.settings.presentation.viewmodel.SettingsViewModel

val LocalWeakHaptic = staticCompositionLocalOf<() -> Unit> { {} }
val LocalStrongHaptic = staticCompositionLocalOf<() -> Unit> { {} }
val LocalDarkMode = staticCompositionLocalOf<Boolean> {
    error("No dark mode provided")
}
val LocalSeedColor = staticCompositionLocalOf<Int> {
    error("No seed color provided")
}
val LocalTonalPalette = staticCompositionLocalOf<List<SeedColors>> {
    error("No tonal palette provided")
}
val LocalNotificationPermission = staticCompositionLocalOf<Boolean> {
    error("No notification preference provided")
}

@Composable
fun CompositionLocals(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val context = LocalContext.current

    val autoUpdate by settingsViewModel.booleanState(SettingsKeys.AUTO_UPDATE)

    val themeMode by settingsViewModel.intState(SettingsKeys.THEME_MODE)

    val seedColor by settingsViewModel.intState(SettingsKeys.SEED_COLOR)

    val isDynamicColor by settingsViewModel.booleanState(SettingsKeys.DYNAMIC_COLORS)

    val isHighContrastDarkMode by settingsViewModel.booleanState(SettingsKeys.HIGH_CONTRAST_DARK_MODE)

    val isHapticEnabled by settingsViewModel.booleanState(SettingsKeys.HAPTICS_AND_VIBRATION)

    val subjectCardCornerRadius by settingsViewModel.floatState(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS)

    val subjectCardStyle by settingsViewModel.intState(SettingsKeys.SUBJECT_CARD_STYLE)

    val githubReleaseType by settingsViewModel.intState(SettingsKeys.GITHUB_RELEASE_TYPE)

    val savedVersionCode by settingsViewModel.intState(SettingsKeys.SAVED_VERSION_CODE)

    val showAttendanceStreaks by settingsViewModel.booleanState(SettingsKeys.STREAK_MODIFIER)

    val rememberCalendarMonthYear by settingsViewModel.booleanState(SettingsKeys.REMEMBER_CALENDAR_MONTH_YEAR)

    val startWeekOnMonday by settingsViewModel.booleanState(SettingsKeys.START_WEEK_ON_MONDAY)

    val enableDirectDownload by settingsViewModel.booleanState(SettingsKeys.ENABLE_DIRECT_DOWNLOAD)

    val notificationPreference by settingsViewModel.booleanState(SettingsKeys.ENABLE_NOTIFICATIONS)

    val notificationPermissionDialogShown by settingsViewModel.booleanState(SettingsKeys.NOTIFICATION_PERMISSION_DIALOG_SHOWN)

    val state =
        remember(
            autoUpdate,
            themeMode,
            seedColor,
            isDynamicColor,
            isHighContrastDarkMode,
            isHapticEnabled,
            subjectCardCornerRadius,
            subjectCardStyle,
            githubReleaseType,
            savedVersionCode,
            showAttendanceStreaks,
            rememberCalendarMonthYear,
            startWeekOnMonday,
            enableDirectDownload,
            notificationPreference,
            notificationPermissionDialogShown
        ) {
            SettingsState(
                isAutoUpdate = autoUpdate,
                themeMode = themeMode,
                isHighContrastDarkMode = isHighContrastDarkMode,
                seedColor = seedColor,
                isDynamicColor = isDynamicColor,
                isHapticEnabled = isHapticEnabled,
                subjectCardCornerRadius = subjectCardCornerRadius,
                subjectCardStyle = subjectCardStyle,
                githubReleaseType = githubReleaseType,
                savedVersionCode = savedVersionCode,
                showAttendanceStreaks = showAttendanceStreaks,
                rememberCalendarMonthYear = rememberCalendarMonthYear,
                startWeekOnMonday = startWeekOnMonday,
                enableDirectDownload = enableDirectDownload,
                notificationPreference = notificationPreference,
                notificationPermissionDialogShown = notificationPermissionDialogShown
            )
        }

    val isDarkTheme = when (themeMode) {
        AppCompatDelegate.MODE_NIGHT_YES -> true
        AppCompatDelegate.MODE_NIGHT_NO -> false
        else -> isSystemInDarkTheme()
    }

    val isNotificationEnabledAndPermitted =
        notificationPreference && isNotificationPermissionGranted(context)

    val tonalPalette = listOf<SeedColors>(
        SeedColors.Blue,
        SeedColors.Indigo,
        SeedColors.Purple,
        SeedColors.Pink,
        SeedColors.Red,
        SeedColors.Orange,
        SeedColors.Yellow,
        SeedColors.Teal,
        SeedColors.Green
    )

    val weakHaptic = remember(isHapticEnabled, view) {
        {
            if (isHapticEnabled) {
                view.weakHaptic()
            }
        }
    }

    val strongHaptic = remember(isHapticEnabled, view) {
        {
            if (isHapticEnabled) {
                view.strongHaptic()
            }
        }
    }

    CompositionLocalProvider(
        LocalSettings provides state,
        LocalWeakHaptic provides weakHaptic,
        LocalStrongHaptic provides strongHaptic,
        LocalSeedColor provides seedColor,
        LocalDarkMode provides isDarkTheme,
        LocalTonalPalette provides tonalPalette,
        LocalNotificationPermission provides isNotificationEnabledAndPermitted
    ) {
        content()
    }
}

@Composable
private fun SettingsViewModel.booleanState(key: SettingsKeys): State<Boolean> {
    return getBoolean(key).collectAsState(initial = key.default as Boolean)
}

@Composable
private fun SettingsViewModel.intState(key: SettingsKeys): State<Int> {
    return getInt(key).collectAsState(initial = key.default as Int)
}

@Composable
private fun SettingsViewModel.floatState(key: SettingsKeys): State<Float> {
    return getFloat(key).collectAsState(initial = key.default as Float)
}

