@file:SuppressLint("RestrictedApi")

package `in`.hridayan.driftly.core.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import `in`.hridayan.driftly.core.utils.a1
import `in`.hridayan.driftly.core.utils.a2
import `in`.hridayan.driftly.core.utils.a3
import `in`.hridayan.driftly.core.utils.n1
import `in`.hridayan.driftly.core.utils.n2

fun lightColorSchemeFromSeed(): ColorScheme {
    return lightColorScheme(
        primary = 40.a1,
        primaryContainer = 90.a1,
        onPrimary = 100.a1,
        onPrimaryContainer = 10.a1,
        inversePrimary = 80.a1,

        secondary = 40.a2,
        secondaryContainer = 90.a2,
        onSecondary = 100.a2,
        onSecondaryContainer = 10.a2,

        tertiary = 40.a3,
        tertiaryContainer = 90.a3,
        onTertiary = 100.a3,
        onTertiaryContainer = 10.a3,

        background = 98.n1,
        onBackground = 10.n1,

        surface = 98.n1,
        onSurface = 10.n1,
        surfaceVariant = 90.n2,
        onSurfaceVariant = 30.n2,
        surfaceDim = 87.n1,
        surfaceBright = 98.n1,
        surfaceContainerLowest = 100.n1,
        surfaceContainerLow = 96.n1,
        surfaceContainer = 94.n1,
        surfaceContainerHigh = 92.n1,
        surfaceContainerHighest = 90.n1,
        inverseSurface = 20.n1,
        inverseOnSurface = 95.n1,

        outline = 50.n2,
        outlineVariant = 80.n2,
    )
}

fun darkColorSchemeFromSeed(): ColorScheme {
    return darkColorScheme(
        primary = 80.a1,
        primaryContainer = 30.a1,
        onPrimary = 20.a1,
        onPrimaryContainer = 90.a1,
        inversePrimary = 40.a1,

        secondary = 80.a2,
        secondaryContainer = 30.a2,
        onSecondary = 20.a2,
        onSecondaryContainer = 90.a2,

        tertiary = 80.a3,
        tertiaryContainer = 30.a3,
        onTertiary = 20.a3,
        onTertiaryContainer = 90.a3,

        background = 6.n1,
        onBackground = 90.n1,

        surface = 6.n1,
        onSurface = 90.n1,
        surfaceVariant = 30.n2,
        onSurfaceVariant = 80.n2,
        surfaceDim = 6.n1,
        surfaceBright = 24.n1,
        surfaceContainerLowest = 4.n1,
        surfaceContainerLow = 10.n1,
        surfaceContainer = 12.n1,
        surfaceContainerHigh = 17.n1,
        surfaceContainerHighest = 22.n1,
        inverseSurface = 90.n1,
        inverseOnSurface = 20.n1,

        outline = 60.n2,
        outlineVariant = 30.n2,
    )
}

