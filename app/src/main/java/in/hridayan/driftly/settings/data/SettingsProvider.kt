package `in`.hridayan.driftly.settings.data

import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys as key
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType

object SettingsProvider {
    val settings = listOf(
        SettingsItem(
            key = key.LOOK_AND_FEEL,
            title = "Look & Feel",
            description = "Dynamic color, Dark theme, Language",
            icon = R.drawable.ic_pallete,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.AUTO_UPDATE,
            title = "Auto update",
            description = "Check for updates automatically",
            icon = R.drawable.ic_update,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.ABOUT,
            title = "About",
            description = "Links and credits",
            icon = R.drawable.ic_info,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = key.HIGH_CONTRAST_DARK_MODE,
            host = LookAndFeelScreen,
            title = "High contrast dark mode",
            description = "Pure black theme for devices with OLED panel",
            icon = R.drawable.ic_contrast,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.DYNAMIC_COLORS,
            host = LookAndFeelScreen,
            title = "Dynamic colors",
            description = "Automatically set the app theme according to the device wallpaper",
            icon = R.drawable.ic_color_picker,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.HAPTICS_AND_VIBRATION,
            host = LookAndFeelScreen,
            title = "Haptics & Vibration",
            description = "Interactive haptics for touch feedback",
            icon = R.drawable.ic_vibration,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = key.LANGUAGE,
            host = LookAndFeelScreen,
            title = "Default language",
            description = "Choose the default language for the app",
            icon = R.drawable.ic_language,
            type = SettingsType.NoSwitch
        ),
    )
}