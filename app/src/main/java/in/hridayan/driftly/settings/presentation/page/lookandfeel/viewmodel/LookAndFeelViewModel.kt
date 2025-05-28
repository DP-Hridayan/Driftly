package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
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
