package `in`.hridayan.driftly.settings.domain.model

import `in`.hridayan.driftly.settings.data.model.SettingsKeys

data class SettingsItem(
    val key: SettingsKeys,
    val titleString: String = "",
    val titleResId: Int = 0, //Error handling needs to be done
    val descriptionString: String = "",
    val descriptionResId: Int = 0, //Error handling needs to be done
    val icon: Int,
    val type: SettingsType = SettingsType.NoSwitch
)