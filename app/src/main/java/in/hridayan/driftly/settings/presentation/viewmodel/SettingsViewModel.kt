package `in`.hridayan.driftly.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.usecase.GetAllSettingsUseCase
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val toggleSettingUseCase: ToggleSettingUseCase,
    private val getAllSettingsUseCase: GetAllSettingsUseCase
) : ViewModel() {

    var settings by mutableStateOf<List<Pair<SettingsItem, Boolean>>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            settings = getAllSettingsUseCase()
        }
    }

    fun onToggle(key: String) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            settings = getAllSettingsUseCase()
        }
    }
}