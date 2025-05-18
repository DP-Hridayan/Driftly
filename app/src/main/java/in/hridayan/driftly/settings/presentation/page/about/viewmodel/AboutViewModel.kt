package `in`.hridayan.driftly.settings.presentation.page.about.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.data.model.SettingsKeys
import `in`.hridayan.driftly.core.common.constants.URL_GITHUB_ISSUE_FEATURE_REQUEST
import `in`.hridayan.driftly.core.common.constants.URL_GITHUB_ISSUE_REPORT
import `in`.hridayan.driftly.core.common.constants.URL_GITHUB_REPO
import `in`.hridayan.driftly.core.common.constants.URL_GITHUB_REPO_LICENSE
import `in`.hridayan.driftly.navigation.ChangelogScreen
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.usecase.GetAboutPageListUseCase
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val getAboutPageListUseCase: GetAboutPageListUseCase
) : ViewModel() {
    var aboutPageList by mutableStateOf<List<Pair<SettingsItem, Flow<Boolean>>>>(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            loadSettings()
        }
    }

    fun onItemClicked(item: SettingsItem) {
        viewModelScope.launch {
            when (item.key) {
                SettingsKeys.CHANGELOGS -> _uiEvent.emit(
                    SettingsUiEvent.Navigate(ChangelogScreen)
                )

                SettingsKeys.REPORT -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(URL_GITHUB_ISSUE_REPORT)
                )

                SettingsKeys.FEATURE_REQUEST -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(URL_GITHUB_ISSUE_FEATURE_REQUEST)
                )

                SettingsKeys.LICENSE -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(URL_GITHUB_REPO_LICENSE)
                )

                SettingsKeys.GITHUB -> _uiEvent.emit(
                    SettingsUiEvent.OpenUrl(URL_GITHUB_REPO)
                )

                else -> {}
            }
        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            val about = getAboutPageListUseCase()
            aboutPageList = about
        }
    }
}
