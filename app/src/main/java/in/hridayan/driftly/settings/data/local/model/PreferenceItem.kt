package `in`.hridayan.driftly.settings.data.local.model

import androidx.compose.ui.graphics.vector.ImageVector
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.SettingsType
import `in`.hridayan.driftly.settings.data.local.model.RadioButtonOptions

sealed class PreferenceItem(
    open val key: SettingsKeys,
    open val isLayoutVisible: Boolean = true,
    open val titleString: String = "",
    open val titleResId: Int = 0,
    open val descriptionString: String = "",
    open val descriptionResId: Int = 0,
    open val iconResId: Int = 0,
    open val iconVector: ImageVector? = null,
    open val type: SettingsType = SettingsType.None
) {
    data class IntPreferenceItem(
        val radioOptions: List<RadioButtonOptions>,
        override val key: SettingsKeys,
        override val isLayoutVisible: Boolean,
        override val titleString: String,
        override val titleResId: Int,
        override val descriptionString: String,
        override val descriptionResId: Int,
        override val iconResId: Int,
        override val iconVector: ImageVector?,
        override val type: SettingsType
    ) : PreferenceItem(key)

    data class BoolPreferenceItem(
        override val key: SettingsKeys,
        override val isLayoutVisible: Boolean,
        override val titleString: String,
        override val titleResId: Int,
        override val descriptionString: String,
        override val descriptionResId: Int,
        override val iconResId: Int,
        override val iconVector: ImageVector?,
        override val type: SettingsType
    ) : PreferenceItem(key)

    data class StringPreferenceItem(
        override val key: SettingsKeys,
        override val isLayoutVisible: Boolean,
        override val titleString: String,
        override val titleResId: Int,
        override val descriptionString: String,
        override val descriptionResId: Int,
        override val iconResId: Int,
        override val iconVector: ImageVector?,
        override val type: SettingsType
    ) : PreferenceItem(key)

    data class FloatPreferenceItem(
        override val key: SettingsKeys,
        override val isLayoutVisible: Boolean,
        override val titleString: String,
        override val titleResId: Int,
        override val descriptionString: String,
        override val descriptionResId: Int,
        override val iconResId: Int,
        override val iconVector: ImageVector?,
        override val type: SettingsType
    ) : PreferenceItem(key)

    data class NullPreferenceItem(
        override val key: SettingsKeys,
        override val isLayoutVisible: Boolean,
        override val titleString: String,
        override val titleResId: Int,
        override val descriptionString: String,
        override val descriptionResId: Int,
        override val iconResId: Int,
        override val iconVector: ImageVector?,
        override val type: SettingsType
    ) : PreferenceItem(key)

    object CustomComposable
}