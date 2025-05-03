package `in`.hridayan.driftly.settings.domain.model

import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.navigation.SettingsScreen

data class SettingsItem (
    val key: SettingsKeys,
    val host:Any = SettingsScreen,
    val title: String,
    val description: String,
    val icon: Int,
    val type: SettingsType = SettingsType.NoSwitch
)