package `in`.hridayan.driftly.core.presentation.provider

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.domain.model.CustomFontFamily

fun getFontFamily(fontFamily: Int): FontFamily? {
    return when (fontFamily) {
        CustomFontFamily.ONE_UI_SANS -> oneUiSans

        CustomFontFamily.SANS_SERIF -> FontFamily.SansSerif

        CustomFontFamily.MONOSPACE -> FontFamily.Monospace

        else -> FontFamily.Default
    }
}

val oneUiSans = FontFamily(
    Font(resId = R.font.one_ui_sans_700, weight = FontWeight.Bold),
    Font(resId = R.font.one_ui_sans_600, weight = FontWeight.SemiBold),
    Font(resId = R.font.one_ui_sans_400, weight = FontWeight.Normal),
)