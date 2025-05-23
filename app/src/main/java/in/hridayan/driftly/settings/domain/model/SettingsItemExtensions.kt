package `in`.hridayan.driftly.settings.domain.model

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.LocalDarkMode
import `in`.hridayan.driftly.core.common.LocalSettings
import `in`.hridayan.driftly.settings.data.SettingsKeys

@Composable
fun SettingsItem.getResolvedIcon(): ImageVector? {

    val darkMode = LocalDarkMode.current

    return if (key == SettingsKeys.DARK_THEME) {
        if (darkMode) Icons.Outlined.DarkMode
        else Icons.Rounded.LightMode
    } else {
        iconVector ?: iconResId.takeIf { it != 0 }?.let {
            runCatching { ImageVector.vectorResource(id = it) }.getOrNull()
        }
    }
}

@Composable
fun SettingsItem.getResolvedTitle(): String {
    return when {
        titleResId != 0 -> runCatching { stringResource(titleResId) }.getOrElse { "" }
        titleString.isNotBlank() -> titleString
        else -> ""
    }
}

@Composable
fun SettingsItem.getResolvedDescription(): String {

    val themeMode = LocalSettings.current.themeMode

    return when {
        key == SettingsKeys.DARK_THEME -> when (themeMode) {
            AppCompatDelegate.MODE_NIGHT_YES -> stringResource(R.string.on)
            AppCompatDelegate.MODE_NIGHT_NO -> stringResource(R.string.off)
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> stringResource(R.string.system)
            else -> ""
        }

        descriptionResId != 0 -> runCatching { stringResource(descriptionResId) }.getOrElse { "" }
        descriptionString.isNotBlank() -> descriptionString
        else -> ""
    }
}
