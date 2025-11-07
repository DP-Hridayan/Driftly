package `in`.hridayan.driftly.settings.presentation.event

import android.content.Intent
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.BackupOption

sealed class SettingsUiEvent {
    data class ShowToast(val message: String) : SettingsUiEvent()
    data class Navigate(val route: Any) : SettingsUiEvent()
    data class ShowDialog(val key : SettingsKeys) : SettingsUiEvent()
    data class ShowBottomSheet(val key : SettingsKeys) : SettingsUiEvent()
    data class OpenUrl(val url:String) : SettingsUiEvent()
    data class LaunchIntent(val intent: Intent) : SettingsUiEvent()
    data class RequestPermission(val permission: String) : SettingsUiEvent()

    data class RequestDocumentUriForBackup(val backupOption: BackupOption) : SettingsUiEvent()
    object RequestDocumentUriForRestore : SettingsUiEvent()
}
