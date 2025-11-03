package `in`.hridayan.driftly.settings.presentation.page.customisation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.domain.model.SubjectCardStyle
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomisationViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val subjectCardCornerRadius = settingsRepository.getFloat(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS)

    fun setSubjectCardCornerRadius(cornerRadius: Float) {
        viewModelScope.launch {
            settingsRepository.setFloat(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS, cornerRadius)
        }
    }

    val cardStyle = settingsRepository
        .getInt(SettingsKeys.SUBJECT_CARD_STYLE)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SubjectCardStyle.CARD_STYLE_A
        )

    fun select(option: Int) {
        viewModelScope.launch {
            settingsRepository.setInt(SettingsKeys.SUBJECT_CARD_STYLE, option)
        }
    }
}