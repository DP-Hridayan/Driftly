@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package `in`.hridayan.driftly.core.presentation.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import `in`.hridayan.driftly.core.presentation.provider.getFontFamily

@Composable
fun appTypography(fontFamily: Int): Typography {
    val fontFamily = getFontFamily(fontFamily) ?: FontFamily.Default
    val base = MaterialTheme.typography

    return Typography(
        displayLarge = base.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = base.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = base.displaySmall.copy(fontFamily = fontFamily),

        headlineLarge = base.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = base.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = base.headlineSmall.copy(fontFamily = fontFamily),

        titleLarge = base.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = base.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = base.titleSmall.copy(fontFamily = fontFamily),

        bodyLarge = base.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = base.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = base.bodySmall.copy(fontFamily = fontFamily),

        labelLarge = base.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = base.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = base.labelSmall.copy(fontFamily = fontFamily),

        // Emphasized variants
        displayLargeEmphasized = base.displayLargeEmphasized.copy(fontFamily = fontFamily),
        displayMediumEmphasized = base.displayMediumEmphasized.copy(fontFamily = fontFamily),
        displaySmallEmphasized = base.displaySmallEmphasized.copy(fontFamily = fontFamily),

        headlineLargeEmphasized = base.headlineLargeEmphasized.copy(fontFamily = fontFamily),
        headlineMediumEmphasized = base.headlineMediumEmphasized.copy(fontFamily = fontFamily),
        headlineSmallEmphasized = base.headlineSmallEmphasized.copy(fontFamily = fontFamily),

        titleLargeEmphasized = base.titleLargeEmphasized.copy(fontFamily = fontFamily),
        titleMediumEmphasized = base.titleMediumEmphasized.copy(fontFamily = fontFamily),
        titleSmallEmphasized = base.titleSmallEmphasized.copy(fontFamily = fontFamily),

        bodyLargeEmphasized = base.bodyLargeEmphasized.copy(fontFamily = fontFamily),
        bodyMediumEmphasized = base.bodyMediumEmphasized.copy(fontFamily = fontFamily),
        bodySmallEmphasized = base.bodySmallEmphasized.copy(fontFamily = fontFamily),

        labelLargeEmphasized = base.labelLargeEmphasized.copy(fontFamily = fontFamily),
        labelMediumEmphasized = base.labelMediumEmphasized.copy(fontFamily = fontFamily),
        labelSmallEmphasized = base.labelSmallEmphasized.copy(fontFamily = fontFamily),
    )
}