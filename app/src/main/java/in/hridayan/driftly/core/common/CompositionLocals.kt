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
import androidx.compose.ui.platform.LocalView
import `in`.hridayan.driftly.core.common.constants.SeedColors
import `in`.hridayan.driftly.core.utils.HapticUtils.strongHaptic
import `in`.hridayan.driftly.core.utils.HapticUtils.weakHaptic
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import `in`.hridayan.driftly.settings.domain.model.SettingsState

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

@Composable
fun CompositionLocals(
    store: SettingsDataStore,
    content: @Composable () -> Unit
) {
    val autoUpdate by store.booleanState(SettingsKeys.AUTO_UPDATE)

    val themeMode by store.intState(SettingsKeys.THEME_MODE)

    val seedColor by store.intState(SettingsKeys.SEED_COLOR)

    val isDynamicColor by store.booleanState(SettingsKeys.DYNAMIC_COLORS)

    val isHighContrastDarkMode by store.booleanState(SettingsKeys.HIGH_CONTRAST_DARK_MODE)

    val isHapticEnabled by store.booleanState(SettingsKeys.HAPTICS_AND_VIBRATION)

    val subjectCardCornerRadius by store.floatState(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS)

    val subjectCardStyle by store.intState(SettingsKeys.SUBJECT_CARD_STYLE)

    val state =
        remember(
            autoUpdate,
            themeMode,
            seedColor,
            isDynamicColor,
            isHighContrastDarkMode,
            isHapticEnabled,
            subjectCardCornerRadius,
            subjectCardStyle
        ) {
            SettingsState(
                isAutoUpdate = autoUpdate,
                isDarkMode = themeMode,
                isHighContrastDarkMode = isHighContrastDarkMode,
                seedColor = seedColor,
                isDynamicColor = isDynamicColor,
                isHapticEnabled = isHapticEnabled,
                subjectCardCornerRadius = subjectCardCornerRadius,
                subjectCardStyle = subjectCardStyle
            )
        }

    val isDarkTheme = when (themeMode) {
        AppCompatDelegate.MODE_NIGHT_YES -> true
        AppCompatDelegate.MODE_NIGHT_NO -> false
        else -> isSystemInDarkTheme()
    }

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

    val view = LocalView.current

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
        LocalTonalPalette provides tonalPalette
    ) {
        content()
    }
}

@Composable
private fun SettingsDataStore.booleanState(key: SettingsKeys): State<Boolean> {
    return isEnabled(key).collectAsState(initial = key.default as Boolean)
}

@Composable
private fun SettingsDataStore.intState(key: SettingsKeys): State<Int> {
    return intFlow(key).collectAsState(initial = key.default as Int)
}

@Composable
private fun SettingsDataStore.floatState(key: SettingsKeys): State<Float> {
    return floatFlow(key).collectAsState(initial = key.default as Float)
}

