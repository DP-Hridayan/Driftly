package `in`.hridayan.driftly.core.presentation.ui.theme

import android.os.Build
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.core.common.constants.MATERIAL_BLUE
import `in`.hridayan.driftly.settings.data.model.SettingsKeys


@Composable
fun DriftlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isHighContrastDarkTheme: Boolean = false,
    seedColor :Int = MATERIAL_BLUE,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val darkColorScheme = darkColorSchemeFromSeed(seedColor)
    val lightColorScheme = lightColorSchemeFromSeed(seedColor)

    LaunchedEffect(darkTheme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (darkTheme) {
                view.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    APPEARANCE_LIGHT_STATUS_BARS,
                )
            } else {
                view.windowInsetsController?.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_STATUS_BARS,
                    APPEARANCE_LIGHT_STATUS_BARS,
                )
            }
        }
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme && isHighContrastDarkTheme) dynamicDarkColorScheme(context).copy(
                surface = Color.Black,
                background = Color.Black,
                surfaceContainerLowest = Color.Black,
            )
            else if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme && isHighContrastDarkTheme -> darkColorScheme.copy(
            surface = Color.Black,
            background = Color.Black,
            surfaceContainerLowest = Color.Black,
        )

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}