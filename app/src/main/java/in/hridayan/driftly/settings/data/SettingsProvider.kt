package `in`.hridayan.driftly.settings.data

import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys as key

object SettingsProvider {
    val settingsPageList = listOf(
        SettingsItem(
            key = key.LOOK_AND_FEEL,
            titleResId = R.string.look_and_feel,
            descriptionResId = R.string.des_look_and_feel,
            icon = R.drawable.ic_pallete,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.AUTO_UPDATE,
            titleResId = R.string.auto_update,
            descriptionResId = R.string.des_auto_update,
            icon = R.drawable.ic_update,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.ABOUT,
            titleResId = R.string.about,
            descriptionResId = R.string.des_about,
            icon = R.drawable.ic_info,
            type = SettingsType.NoSwitch
        )
    )

    val lookAndFeelPageList = listOf(
        SettingsItem(
            key = key.HIGH_CONTRAST_DARK_MODE,
            titleResId = R.string.high_contrast_dark_mode,
            descriptionResId = R.string.des_high_contrast_dark_mode,
            icon = R.drawable.ic_contrast,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.DYNAMIC_COLORS,
            titleResId = R.string.dynamic_colors,
            descriptionResId = R.string.des_dynamic_colors,
            icon = R.drawable.ic_color_picker,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.HAPTICS_AND_VIBRATION,
            titleResId = R.string.haptics_and_vibration,
            descriptionResId = R.string.des_haptics_and_vibration,
            icon = R.drawable.ic_vibration,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.LANGUAGE,
            titleResId = R.string.default_language,
            descriptionResId = R.string.des_default_language,
            icon = R.drawable.ic_language,
            type = SettingsType.NoSwitch
        ),
    )
}