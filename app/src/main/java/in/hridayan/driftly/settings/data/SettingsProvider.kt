package `in`.hridayan.driftly.settings.data

import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.SettingsType

object SettingsProvider {
    val settings = listOf(
        SettingsItem(
            key = "look_and_feel",
            title = "Look & Feel",
            description = "Dynamic color, Dark theme, Language",
            icon = R.drawable.ic_pallete,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = "auto_update",
            title = "Auto update",
            description = "Check for updates automatically",
            icon = R.drawable.ic_update,
            type = SettingsType.Switch
        ),
        SettingsItem(
            key = "about",
            title = "About",
            description = "Links and credits",
            icon = R.drawable.ic_info,
            type = SettingsType.NoSwitch
        ),
        SettingsItem(
            key = "language",
            host = LookAndFeelScreen,
            title = "Default language",
            description = "Choose the default language for the app",
            icon = R.drawable.ic_language,
            type = SettingsType.NoSwitch
        ),
    )
}