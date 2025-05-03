package `in`.hridayan.driftly.settings.page.lookandfeel.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.settings.data.SettingsDataStore
import `in`.hridayan.driftly.settings.page.lookandfeel.domain.ThemeOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val store: SettingsDataStore
) : ViewModel() {
    val themeOption = store
        .intFlow(SettingsKeys.THEME_MODE, default = ThemeOption.SYSTEM.mode)
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
}
