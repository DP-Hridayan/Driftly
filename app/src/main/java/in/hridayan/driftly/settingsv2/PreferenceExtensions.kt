package `in`.hridayan.driftly.settingsv2

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
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsType

fun category(titleResId: Int, vararg items: PreferenceItem): PreferenceGroup.Category {
    return PreferenceGroup.Category(titleResId, items.toList())
}

fun uncategorizedItems(vararg items: PreferenceItem): PreferenceGroup.Items {
    return PreferenceGroup.Items(items.toList())
}


fun intPreferenceItem(
    key: SettingsKeys,
    options: List<Int>,
    default: Int,
    titleString: String = "",
    titleResId: Int = 0,
    descriptionString: String = "",
    descriptionResId: Int = 0,
    iconResId: Int = 0,
    iconVector: ImageVector? = null,
    type: SettingsType = SettingsType.NoSwitch
) = PreferenceItem.IntPreferenceItem(
    key = key,
    options = options,
    default = default,
    titleString = titleString,
    titleResId = titleResId,
    descriptionString = descriptionString,
    descriptionResId = descriptionResId,
    iconResId = iconResId,
    iconVector = iconVector,
    type = type
)

fun boolPreferenceItem(
    key: SettingsKeys,
    titleString: String = "",
    titleResId: Int = 0,
    descriptionString: String = "",
    descriptionResId: Int = 0,
    iconResId: Int = 0,
    iconVector: ImageVector? = null,
    type: SettingsType = SettingsType.Switch
) = PreferenceItem.BoolPreferenceItem(
    key = key,
    titleString = titleString,
    titleResId = titleResId,
    descriptionString = descriptionString,
    descriptionResId = descriptionResId,
    iconResId = iconResId,
    iconVector = iconVector,
    type = type
)

fun stringPreferenceItem(
    key: SettingsKeys,
    default: String,
    titleString: String = "",
    titleResId: Int = 0,
    descriptionString: String = "",
    descriptionResId: Int = 0,
    iconResId: Int = 0,
    iconVector: ImageVector? = null,
    type: SettingsType = SettingsType.NoSwitch
) = PreferenceItem.StringPreferenceItem(
    key = key,
    default = default,
    titleString = titleString,
    titleResId = titleResId,
    descriptionString = descriptionString,
    descriptionResId = descriptionResId,
    iconResId = iconResId,
    iconVector = iconVector,
    type = type
)

fun floatPreferenceItem(
    key: SettingsKeys,
    default: Float,
    titleString: String = "",
    titleResId: Int = 0,
    descriptionString: String = "",
    descriptionResId: Int = 0,
    iconResId: Int = 0,
    iconVector: ImageVector? = null,
    type: SettingsType = SettingsType.NoSwitch
) = PreferenceItem.FloatPreferenceItem(
    key = key,
    default = default,
    titleString = titleString,
    titleResId = titleResId,
    descriptionString = descriptionString,
    descriptionResId = descriptionResId,
    iconResId = iconResId,
    iconVector = iconVector,
    type = type
)

fun nullPreferenceItem(
    key: SettingsKeys,
    titleString: String = "",
    titleResId: Int = 0,
    descriptionString: String = "",
    descriptionResId: Int = 0,
    iconResId: Int = 0,
    iconVector: ImageVector? = null,
    type: SettingsType = SettingsType.NoSwitch
) = PreferenceItem.NullPreferenceItem(
    key = key,
    titleString = titleString,
    titleResId = titleResId,
    descriptionString = descriptionString,
    descriptionResId = descriptionResId,
    iconResId = iconResId,
    iconVector = iconVector,
    type = type
)

fun customComposable() = PreferenceItem.CustomComposable

@Composable
fun PreferenceItem.getResolvedTitle(): String {
    return when {
        titleResId != 0 -> runCatching { stringResource(titleResId) }.getOrElse { "" }
        titleString.isNotBlank() -> titleString
        else -> ""
    }
}

@Composable
fun PreferenceItem.getResolvedIcon(): ImageVector? {

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
fun PreferenceItem.getResolvedDescription(): String {

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
