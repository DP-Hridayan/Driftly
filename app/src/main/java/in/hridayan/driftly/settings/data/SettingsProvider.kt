package `in`.hridayan.driftly.settings.data

import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.domain.model.SettingsItem

object SettingsProvider {
    val settings = listOf(
        SettingsItem(
            key = "dark_mode",
            title = "Dark Mode",
            description = "Enable or disable dark theme",
            icon = R.drawable.ic_edit,
            isToggleable = true
        ),
        SettingsItem(
            key = "notifications",
            title = "Notifications",
            description = "Manage notification settings",
            icon = R.drawable.ic_delete,
            isToggleable = true
        )
        // Add more if needed
    )
}