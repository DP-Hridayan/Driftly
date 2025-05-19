package `in`.hridayan.driftly.core.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.google.android.material.color.utilities.CorePalette

@SuppressLint("RestrictedApi")
fun lightColorSchemeFromSeed(seed: Int): ColorScheme {
    val palette = CorePalette.of(seed)

    return lightColorScheme(
        primary = Color(palette.a1.getHct(40.0).toInt()),
        primaryContainer = Color(palette.a1.getHct(90.0).toInt()),
        onPrimary = Color(palette.a1.getHct(100.0).toInt()),
        onPrimaryContainer = Color(palette.a1.getHct(10.0).toInt()),
        inversePrimary = Color(palette.a1.getHct(80.0).toInt()),

        secondary = Color(palette.a2.getHct(40.0).toInt()),
        secondaryContainer = Color(palette.a2.getHct(90.0).toInt()),
        onSecondary = Color(palette.a2.getHct(100.0).toInt()),
        onSecondaryContainer = Color(palette.a2.getHct(10.0).toInt()),

        tertiary = Color(palette.a3.getHct(40.0).toInt()),
        tertiaryContainer = Color(palette.a3.getHct(90.0).toInt()),
        onTertiary = Color(palette.a3.getHct(100.0).toInt()),
        onTertiaryContainer = Color(palette.a3.getHct(10.0).toInt()),

        background = Color(palette.n1.getHct(98.0).toInt()),
        onBackground = Color(palette.n1.getHct(10.0).toInt()),

        surface = Color(palette.n1.getHct(98.0).toInt()),
        onSurface = Color(palette.n1.getHct(10.0).toInt()),
        surfaceVariant = Color(palette.n2.getHct(90.0).toInt()),
        onSurfaceVariant = Color(palette.n2.getHct(30.0).toInt()),
        surfaceDim = Color(palette.n1.getHct(87.0).toInt()),
        surfaceBright = Color(palette.n1.getHct(98.0).toInt()),
        surfaceContainerLowest = Color(palette.n1.getHct(100.0).toInt()),
        surfaceContainerLow = Color(palette.n1.getHct(96.0).toInt()),
        surfaceContainer = Color(palette.n1.getHct(94.0).toInt()),
        surfaceContainerHigh = Color(palette.n1.getHct(92.0).toInt()),
        surfaceContainerHighest = Color(palette.n1.getHct(90.0).toInt()),
        inverseSurface = Color(palette.n1.getHct(20.0).toInt()),
        inverseOnSurface = Color(palette.n1.getHct(95.0).toInt()),

        outline = Color(palette.n2.getHct(50.0).toInt()),
        outlineVariant = Color(palette.n2.getHct(80.0).toInt()),
    )
}

@SuppressLint("RestrictedApi")
fun darkColorSchemeFromSeed(seed: Int): ColorScheme {
    val palette = CorePalette.of(seed)

    return darkColorScheme(
        primary = Color(palette.a1.getHct(80.0).toInt()),
        primaryContainer = Color(palette.a1.getHct(30.0).toInt()),
        onPrimary = Color(palette.a1.getHct(20.0).toInt()),
        onPrimaryContainer = Color(palette.a1.getHct(90.0).toInt()),
        inversePrimary = Color(palette.a1.getHct(40.0).toInt()),

        secondary = Color(palette.a2.getHct(80.0).toInt()),
        secondaryContainer = Color(palette.a2.getHct(30.0).toInt()),
        onSecondary = Color(palette.a2.getHct(20.0).toInt()),
        onSecondaryContainer = Color(palette.a2.getHct(90.0).toInt()),

        tertiary = Color(palette.a3.getHct(80.0).toInt()),
        tertiaryContainer = Color(palette.a3.getHct(30.0).toInt()),
        onTertiary = Color(palette.a3.getHct(20.0).toInt()),
        onTertiaryContainer = Color(palette.a3.getHct(90.0).toInt()),

        background = Color(palette.n1.getHct(6.0).toInt()),
        onBackground = Color(palette.n1.getHct(90.0).toInt()),

        surface = Color(palette.n1.getHct(6.0).toInt()),
        onSurface = Color(palette.n1.getHct(90.0).toInt()),
        surfaceVariant = Color(palette.n2.getHct(30.0).toInt()),
        onSurfaceVariant = Color(palette.n2.getHct(80.0).toInt()),
        surfaceDim = Color(palette.n1.getHct(6.0).toInt()),
        surfaceBright = Color(palette.n1.getHct(24.0).toInt()),
        surfaceContainerLowest = Color(palette.n1.getHct(4.0).toInt()),
        surfaceContainerLow = Color(palette.n1.getHct(10.0).toInt()),
        surfaceContainer = Color(palette.n1.getHct(12.0).toInt()),
        surfaceContainerHigh = Color(palette.n1.getHct(17.0).toInt()),
        surfaceContainerHighest = Color(palette.n1.getHct(22.0).toInt()),
        inverseSurface = Color(palette.n1.getHct(90.0).toInt()),
        inverseOnSurface = Color(palette.n1.getHct(20.0).toInt()),

        outline = Color(palette.n2.getHct(60.0).toInt()),
        outlineVariant = Color(palette.n2.getHct(30.0).toInt()),
    )
}

