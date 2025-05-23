package `in`.hridayan.driftly.settings.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.navigation.AboutScreen
import `in`.hridayan.driftly.navigation.AutoUpdateScreen
import `in`.hridayan.driftly.navigation.BehaviorScreen
import `in`.hridayan.driftly.navigation.CustomisationScreen
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.GetAboutPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetBehaviorPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetDynamicColorSettingUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetHighContrastDarkThemeSettingUseCase
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
    private val settingsRepository: SettingsRepository,
    private val toggleSettingUseCase: ToggleSettingUseCase,
    private val getSettingsPageListUseCase: GetSettingsPageListUseCase,
    private val getLookAndFeelPageListUseCase: GetLookAndFeelPageListUseCase,
    private val getAboutPageListUseCase: GetAboutPageListUseCase,
    private val getDynamicColorSettingUseCase: GetDynamicColorSettingUseCase,
    private val getHighContrastDarkThemeSettingUseCase: GetHighContrastDarkThemeSettingUseCase,
    private val getBehaviorPageListUseCase: GetBehaviorPageListUseCase
) : ViewModel() {

    var settingsPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    var lookAndFeelPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    var aboutPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    var behaviorPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    var dynamicColorSetting by mutableStateOf<Pair<SettingsItem, Flow<Boolean>>?>(null)
        private set

    var highContrastDarkThemeSetting by mutableStateOf<Pair<SettingsItem, Flow<Boolean>>?>(null)
        private set

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            loadSettings()
        }
    }

    fun onToggle(key: SettingsKeys) {
        viewModelScope.launch {
            toggleSettingUseCase(key)
            loadSettings()
        }
    }

    fun setBoolean(key: SettingsKeys, value: Boolean) {
        viewModelScope.launch {
            settingsRepository.setBoolean(key, value)
        }
    }

    fun getBoolean(key: SettingsKeys): Flow<Boolean> = settingsRepository.getBoolean(key)

    fun setInt(key: SettingsKeys, value: Int) {
        viewModelScope.launch {
            settingsRepository.setInt(key, value)
        }
    }

    fun getInt(key: SettingsKeys): Flow<Int> = settingsRepository.getInt(key)

    fun setFloat(key: SettingsKeys, value: Float) {
        viewModelScope.launch {
            settingsRepository.setFloat(key, value)
        }
    }

    fun getFloat(key: SettingsKeys): Flow<Float> = settingsRepository.getFloat(key)

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when (item.key) {
                SettingsKeys.LOOK_AND_FEEL -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(LookAndFeelScreen)
                )

                SettingsKeys.CUSTOMISATION -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(CustomisationScreen)
                )

                SettingsKeys.AUTO_UPDATE -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(AutoUpdateScreen)
                )

                SettingsKeys.BEHAVIOR -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(BehaviorScreen)
                )

                SettingsKeys.ABOUT -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(AboutScreen)
                )

                else -> {}
            }
        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            val lookAndFeel = getLookAndFeelPageListUseCase()
            val settings = getSettingsPageListUseCase()
            val about = getAboutPageListUseCase()
            val behavior = getBehaviorPageListUseCase()
            val dynamicColor = getDynamicColorSettingUseCase()
            val highContrastDarkTheme = getHighContrastDarkThemeSettingUseCase()

            settingsPageList = settings
            lookAndFeelPageList = lookAndFeel
            aboutPageList = about
            behaviorPageList = behavior
            dynamicColorSetting = dynamicColor
            highContrastDarkThemeSetting = highContrastDarkTheme
        }
    }
}
