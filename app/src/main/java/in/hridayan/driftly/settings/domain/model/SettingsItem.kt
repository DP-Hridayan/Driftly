package `in`.hridayan.driftly.settings.domain.model

data class SettingsItem (
    val key: String,
    val title: String,
    val description: String,
    val icon: Int,
    val isToggleable: Boolean = false,
)