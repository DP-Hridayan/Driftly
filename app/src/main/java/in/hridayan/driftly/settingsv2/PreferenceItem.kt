package `in`.hridayan.driftly.settingsv2

import androidx.compose.ui.graphics.vector.ImageVector
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsType

sealed class PreferenceItem(
    open val key: SettingsKeys, open val titleString: String = "",
    open val titleResId: Int = 0,
    open val descriptionString: String = "",
    open val descriptionResId: Int = 0,
    open val iconResId: Int = 0,
    open val iconVector: ImageVector? = null
) {
    data class IntPreferenceItem(
        val options: List<Int>,
        val default: Int,
        override val key: SettingsKeys,
        override val titleString: String = "",
        override val titleResId: Int = 0,
        override val descriptionString: String = "",
        override val descriptionResId: Int = 0,
        override val iconResId: Int = 0,
        override val iconVector: ImageVector? = null,
        val type: SettingsType = SettingsType.NoSwitch
    ) : PreferenceItem(key)

    data class BoolPreferenceItem(
        override val key: SettingsKeys,
        override val titleString: String = "",
        override val titleResId: Int = 0,
        override val descriptionString: String = "",
        override val descriptionResId: Int = 0,
        override val iconResId: Int = 0,
        override val iconVector: ImageVector? = null,
        val type: SettingsType = SettingsType.Switch
    ) : PreferenceItem(key)

    data class StringPreferenceItem(
        val default: String,
        override val key: SettingsKeys,
        override val titleString: String = "",
        override val titleResId: Int = 0,
        override val descriptionString: String = "",
        override val descriptionResId: Int = 0,
        override val iconResId: Int = 0,
        override val iconVector: ImageVector? = null,
        val type: SettingsType = SettingsType.NoSwitch
    ) : PreferenceItem(key)

    data class FloatPreferenceItem(
        val default: Float,
        override val key: SettingsKeys,
        override val titleString: String = "",
        override val titleResId: Int = 0,
        override val descriptionString: String = "",
        override val descriptionResId: Int = 0,
        override val iconResId: Int = 0,
        override val iconVector: ImageVector? = null,
        val type: SettingsType = SettingsType.NoSwitch
    ) : PreferenceItem(key)

    data class NullPreferenceItem(
        override val key: SettingsKeys,
        override val titleString: String = "",
        override val titleResId: Int = 0,
        override val descriptionString: String = "",
        override val descriptionResId: Int = 0,
        override val iconResId: Int = 0,
        override val iconVector: ImageVector? = null,
        val type: SettingsType = SettingsType.NoSwitch
    ) : PreferenceItem(key)

    object CustomComposable
}
