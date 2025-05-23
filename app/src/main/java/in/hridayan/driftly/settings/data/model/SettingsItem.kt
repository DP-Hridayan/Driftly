package `in`.hridayan.driftly.settings.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsType

data class SettingsItem(
    val key: SettingsKeys,
    val titleString: String = "",
    val titleResId: Int = 0,
    val descriptionString: String = "",
    val descriptionResId: Int = 0,
    val iconResId: Int = 0,
    val iconVector: ImageVector? = null,
    val type: SettingsType = SettingsType.NoSwitch
)