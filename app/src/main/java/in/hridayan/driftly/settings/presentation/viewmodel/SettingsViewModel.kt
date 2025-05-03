package `in`.hridayan.driftly.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.navigation.SettingsScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.usecase.GetAllSettingsUseCase
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val toggleSettingUseCase: ToggleSettingUseCase,
    private val getAllSettingsUseCase: GetAllSettingsUseCase
) : ViewModel() {

    private var currentHost: Any? = SettingsScreen

    var settings by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun loadSettingsForHost(host: Any? = SettingsScreen) {
        viewModelScope.launch {
            currentHost = host
            val allSettings = getAllSettingsUseCase()
            settings = filterByHost(allSettings, host)
        }
    }

    fun onToggle(key: SettingsKeys) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            val allSettings = getAllSettingsUseCase()
            settings = filterByHost(allSettings, currentHost)
        }
    }

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when (item.key) {
                SettingsKeys.LOOK_AND_FEEL -> _uiEvent.emit(SettingsUiEvent.Navigate(LookAndFeelScreen))
                else -> {}
            }
        }
    }

    private fun filterByHost(
        fullList: List<Pair<SettingsItem, Flow<Boolean>>>,
        host: Any?
    ): List<Pair<SettingsItem, Flow<Boolean>>> {
        return if (host != null) {
            fullList.filter { it.first.host::class == host::class }
        } else {
            fullList
        }
    }
}
