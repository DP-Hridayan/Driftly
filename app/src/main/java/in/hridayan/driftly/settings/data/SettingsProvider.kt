package `in`.hridayan.driftly.settings.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.ChangeHistory
import androidx.compose.material.icons.rounded.Colorize
import androidx.compose.material.icons.rounded.Contrast
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material.icons.rounded.Vibration
import `in`.hridayan.driftly.BuildConfig
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.data.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType
import `in`.hridayan.driftly.settings.data.SettingsKeys as key

object SettingsProvider {
    val settingsPageList = listOf<SettingsItem>(
        SettingsItem(
            key = key.LOOK_AND_FEEL,
            titleResId = R.string.look_and_feel,
            descriptionResId = R.string.des_look_and_feel,
            iconVector = Icons.Outlined.Palette,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.CUSTOMISATION,
            titleResId = R.string.customisation,
            descriptionResId = R.string.des_customisation,
            iconVector = Icons.Rounded.Tune,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.AUTO_UPDATE,
            titleResId = R.string.auto_update,
            descriptionResId = R.string.des_auto_update,
            iconVector = Icons.Rounded.Update,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.ABOUT,
            titleResId = R.string.about,
            descriptionResId = R.string.des_about,
            iconResId = R.drawable.ic_info,
            type = SettingsType.NoSwitch
        )
    )

    val dynamicColorSetting = SettingsItem(
        key = key.DYNAMIC_COLORS,
        titleResId = R.string.dynamic_colors,
        descriptionResId = R.string.des_dynamic_colors,
        iconVector = Icons.Rounded.Colorize,
        type = SettingsType.Switch
    )

    val highContrastDarkThemeSetting = SettingsItem(
        key = key.HIGH_CONTRAST_DARK_MODE,
        titleResId = R.string.high_contrast_dark_mode,
        descriptionResId = R.string.des_high_contrast_dark_mode,
        iconVector = Icons.Rounded.Contrast,
        type = SettingsType.Switch
    )

    val lookAndFeelPageList = listOf<SettingsItem>(
        SettingsItem(
            key = key.DARK_THEME,
            titleResId = R.string.dark_theme,
            descriptionResId = R.string.system,
            iconVector = Icons.Outlined.DarkMode,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.HAPTICS_AND_VIBRATION,
            titleResId = R.string.haptics_and_vibration,
            descriptionResId = R.string.des_haptics_and_vibration,
            iconVector = Icons.Rounded.Vibration,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.LANGUAGE,
            titleResId = R.string.default_language,
            descriptionResId = R.string.des_default_language,
            iconVector = Icons.Rounded.Language,
            type = SettingsType.NoSwitch
        ),
    )

    val aboutPageList = listOf<SettingsItem>(
        SettingsItem(
            key = key.VERSION,
            titleResId = R.string.version,
            descriptionString = BuildConfig.VERSION_NAME,
            iconResId = R.drawable.ic_version,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.CHANGELOGS,
            titleResId = R.string.changelogs,
            descriptionResId = R.string.des_changelogs,
            iconVector = Icons.Rounded.ChangeHistory,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.REPORT,
            titleResId = R.string.report_issue,
            descriptionResId = R.string.des_report_issue,
            iconResId = R.drawable.ic_report,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.FEATURE_REQUEST,
            titleResId = R.string.feature_request,
            descriptionResId = R.string.des_feature_request,
            iconVector = Icons.Rounded.AddComment,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.GITHUB,
            titleResId = R.string.github,
            descriptionResId = R.string.des_github,
            iconResId = R.drawable.ic_github,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.LICENSE,
            titleResId = R.string.license,
            descriptionResId = R.string.des_license,
            iconResId = R.drawable.ic_license,
            type = SettingsType.NoSwitch
        )
    )
}