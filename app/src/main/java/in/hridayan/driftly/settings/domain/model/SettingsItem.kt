package `in`.hridayan.driftly.settings.domain.model

import android.R
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.navigation.SettingsScreen

data class SettingsItem (
    val key: SettingsKeys,
    val titleResId: Int,
    val descriptionResId: Int,
    val icon: Int,
    val type: SettingsType = SettingsType.NoSwitch
)