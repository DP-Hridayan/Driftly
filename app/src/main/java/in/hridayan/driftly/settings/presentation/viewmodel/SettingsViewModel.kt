package `in`.hridayan.driftly.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.navigation.SettingsScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.usecase.GetAllSettingsUseCase
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val toggleSettingUseCase: ToggleSettingUseCase,
    private val getAllSettingsUseCase: GetAllSettingsUseCase
) : ViewModel() {

    var settings by mutableStateOf<List<Pair<SettingsItem, Boolean>>>(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadSettingsForHost(host: Any? = SettingsScreen) {
        viewModelScope.launch {
            val allSettings = getAllSettingsUseCase()
            settings = if (host != null) {
                allSettings.filter { it.first.host::class == host::class }
            } else {
                allSettings
            }
        }
    }


    fun onToggle(key: String) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            settings = getAllSettingsUseCase()
        }
    }

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when(item.key){
                "look_and_feel" -> {
                    _uiEvent.emit(SettingsUiEvent.Navigate(LookAndFeelScreen))
                }
            }
        }
    }
}
