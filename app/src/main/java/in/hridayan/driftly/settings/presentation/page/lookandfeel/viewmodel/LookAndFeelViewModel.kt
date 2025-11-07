package `in`.hridayan.driftly.settings.presentation.page.lookandfeel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.presentation.provider.SeedColor
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LookAndFeelViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private var lastSeed: SeedColor? = null

    private val _isCheckedMatchCase = MutableStateFlow(false)
    val isCheckedMatchCase: StateFlow<Boolean> = _isCheckedMatchCase

    private val _isCheckedBold = MutableStateFlow(false)
    val isCheckedBold: StateFlow<Boolean> = _isCheckedBold

    private val _isCheckedItalic = MutableStateFlow(false)
    val isCheckedItalic: StateFlow<Boolean> = _isCheckedItalic

    private val _isCheckedUnderline = MutableStateFlow(false)
    val isCheckedUnderline: StateFlow<Boolean> = _isCheckedUnderline

    fun setSeedColor(seed: SeedColor) {
        if (seed == lastSeed) return
        lastSeed = seed

        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setInt(SettingsKeys.PRIMARY_SEED, seed.primary)
            settingsRepository.setInt(SettingsKeys.SECONDARY_SEED, seed.secondary)
            settingsRepository.setInt(SettingsKeys.TERTIARY_SEED, seed.tertiary)
        }
    }

    fun disableDynamicColors() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setBoolean(SettingsKeys.DYNAMIC_COLORS, false)
        }
    }

    fun toggleMatchCase() {
        _isCheckedMatchCase.value = !_isCheckedMatchCase.value
    }

    fun toggleBold() {
        _isCheckedBold.value = !_isCheckedBold.value
    }

    fun toggleItalic() {
        _isCheckedItalic.value = !_isCheckedItalic.value
    }

    fun toggleUnderline() {
        _isCheckedUnderline.value = !_isCheckedUnderline.value
    }

    fun formatClear() {
        _isCheckedMatchCase.value = false
        _isCheckedBold.value = false
        _isCheckedItalic.value = false
        _isCheckedUnderline.value = false
    }
}
