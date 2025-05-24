package `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.CheckUpdateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoUpdateViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val checkUpdateUseCase: CheckUpdateUseCase
) : ViewModel() {
    private val _updateEvents = MutableSharedFlow<UpdateResult>()
    val updateEvents = _updateEvents.asSharedFlow()

    fun checkForUpdates(currentVersion: String, includePrerelease: Boolean) {
        viewModelScope.launch {
            val result = checkUpdateUseCase(currentVersion, includePrerelease)
            _updateEvents.emit(result)
        }
    }

    val githubReleaseType = settingsRepository
        .getInt(SettingsKeys.GITHUB_RELEASE_TYPE)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = GithubReleaseType.STABLE
        )

    fun select(option: Int) {
        viewModelScope.launch {
            settingsRepository.setInt(SettingsKeys.GITHUB_RELEASE_TYPE, option)
        }
    }
}