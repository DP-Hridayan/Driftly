package `in`.hridayan.driftly.settings.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.common.constants.UrlConst
import `in`.hridayan.driftly.navigation.AboutScreen
import `in`.hridayan.driftly.navigation.AutoUpdateScreen
import `in`.hridayan.driftly.navigation.BehaviorScreen
import `in`.hridayan.driftly.navigation.ChangelogScreen
import `in`.hridayan.driftly.navigation.CustomisationScreen
import `in`.hridayan.driftly.navigation.DarkThemeScreen
import `in`.hridayan.driftly.navigation.LookAndFeelScreen
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.GetAboutPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetAutoUpdatePageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetBehaviorPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetDarkThemePageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetLookAndFeelPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.GetSettingsPageListUseCase
import `in`.hridayan.driftly.settings.domain.usecase.ToggleSettingUseCase
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val toggleSettingUseCase: ToggleSettingUseCase,
    private val getSettingsPageListUseCase: GetSettingsPageListUseCase,
    private val getLookAndFeelPageListUseCase: GetLookAndFeelPageListUseCase,
    private val getAboutPageListUseCase: GetAboutPageListUseCase,
    private val getAutoUpdatePageListUseCase: GetAutoUpdatePageListUseCase,
    private val getDarkThemePageListUseCase: GetDarkThemePageListUseCase,
    private val getBehaviorPageListUseCase: GetBehaviorPageListUseCase
) : ViewModel() {
    var settingsPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var lookAndFeelPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var darkThemePageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var aboutPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var autoUpdatePageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
        private set

    var behaviorPageList by mutableStateOf<List<PreferenceGroup>>(emptyList())
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

    fun onItemClicked(key: SettingsKeys) {
        viewModelScope.launch {
            when (key) {
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

                SettingsKeys.CHANGELOGS -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(ChangelogScreen)
                )

                SettingsKeys.REPORT -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_ISSUE_REPORT)
                )

                SettingsKeys.FEATURE_REQUEST -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_ISSUE_FEATURE_REQUEST)
                )

                SettingsKeys.LICENSE -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_REPO_LICENSE)
                )

                SettingsKeys.GITHUB -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(UrlConst.URL_GITHUB_REPO)
                )

                SettingsKeys.LANGUAGE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    _uiEvent.emit(
                        SettingsUiEvent.LaunchIntent(
                            intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS).apply {
                                data = "package:${context.packageName}".toUri()
                            }
                        )
                    )
                }

                SettingsKeys.DARK_THEME -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(DarkThemeScreen)
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
            val autoUpdate = getAutoUpdatePageListUseCase()
            val behavior = getBehaviorPageListUseCase()
            val darkTheme = getDarkThemePageListUseCase()

            settingsPageList = settings
            autoUpdatePageList = autoUpdate
            lookAndFeelPageList = lookAndFeel
            aboutPageList = about
            behaviorPageList = behavior
            darkThemePageList = darkTheme
        }
    }
}
