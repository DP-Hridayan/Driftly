package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.navigation.DarkThemeScreen
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.model.ThemeOption
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LookAndFeelViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    fun setSeedColor(seed: Int) {
        viewModelScope.launch {
            settingsRepository.setInt(SettingsKeys.SEED_COLOR, seed)
        }
    }

    fun disableDynamicColors() {
        viewModelScope.launch {
            settingsRepository.setBoolean(SettingsKeys.DYNAMIC_COLORS, false)
        }
    }
}
