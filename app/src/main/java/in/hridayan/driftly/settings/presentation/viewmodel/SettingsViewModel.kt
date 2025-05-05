package `in`.hridayan.driftly.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.usecase.GetLookAndFeelPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetSettingsPageListUseCase
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
    private val getSettingsPageListUseCase: GetSettingsPageListUseCase,
    private val getLookAndFeelPageListUseCase: GetLookAndFeelPageListUseCase,
) : ViewModel() {

    var settingsPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    var lookAndFeelPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun onToggle(key: SettingsKeys) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            loadSettings()
        }
    }

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when (item.key) {
                SettingsKeys.LOOK_AND_FEEL -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(
                        LookAndFeelScreen
                    )
                )

                else -> {}
            }
        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            val lookAndFeel = getLookAndFeelPageListUseCase()
            val settings = getSettingsPageListUseCase()
            settingsPageList = settings
            lookAndFeelPageList = lookAndFeel
        }
    }
}
