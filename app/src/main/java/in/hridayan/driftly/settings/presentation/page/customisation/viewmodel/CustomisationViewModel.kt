package `in`.hridayan.driftly.settings.presentation.page.customisation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.model.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomisationViewModel @Inject constructor(
    private val store: SettingsDataStore,
) : ViewModel() {
     val subjectCardCornerRadius = store.floatFlow(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS)

    fun setSubjectCardCornerRadius(cornerRadius: Float) {
        viewModelScope.launch {
            store.setFLoat(SettingsKeys.SUBJECT_CARD_CORNER_RADIUS, cornerRadius)
        }
    }
}