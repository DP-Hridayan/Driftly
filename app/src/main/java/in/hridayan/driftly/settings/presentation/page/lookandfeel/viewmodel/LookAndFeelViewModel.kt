package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.navigation.DarkThemeScreen
import `in`.hridayan.driftly.settings.data.model.SettingsKeys
import `in`.hridayan.driftly.settings.data.SettingsDataStore
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import `in`.hridayan.driftly.settings.presentation.page.lookandfeel.domain.ThemeOption
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LookAndFeelViewModel @Inject constructor(
    private val store: SettingsDataStore,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val themeOption = store
        .intFlow(SettingsKeys.THEME_MODE)
        .map { ThemeOption.fromMode(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeOption.SYSTEM
        )

    fun select(option: ThemeOption) {
        viewModelScope.launch {
            store.setInt(SettingsKeys.THEME_MODE, option.mode)
            AppCompatDelegate.setDefaultNightMode(option.mode)
        }
    }

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when (item.key) {
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
}
