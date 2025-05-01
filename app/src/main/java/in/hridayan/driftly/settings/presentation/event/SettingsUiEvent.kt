package `in`.hridayan.driftly.settings.presentation.event

sealed class SettingsUiEvent {
    data class ShowToast(val message: String) : SettingsUiEvent()
    data class Navigate(val route: Any) : SettingsUiEvent()
    data class ShowDialog(val title: String) : SettingsUiEvent()
}
