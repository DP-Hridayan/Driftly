package `in`.hridayan.driftly.settings.presentation.event

import android.content.Intent
import `in`.hridayan.driftly.settings.data.local.SettingsKeys

sealed class SettingsUiEvent {
    data class ShowToast(val message: String) : SettingsUiEvent()
    data class Navigate(val route: Any) : SettingsUiEvent()
    data class ShowDialog(val key : SettingsKeys) : SettingsUiEvent()
    data class OpenUrl(val url:String) : SettingsUiEvent()
    data class LaunchIntent(val intent: Intent) : SettingsUiEvent()
}
