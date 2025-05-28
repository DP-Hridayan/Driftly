package `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.core.domain.model.DownloadState
import `in`.hridayan.driftly.core.domain.usecase.DownloadApkUseCase
import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import `in`.hridayan.driftly.settings.domain.usecase.CheckUpdateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoUpdateViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val checkUpdateUseCase: CheckUpdateUseCase,
    private val downloadApkUseCase: DownloadApkUseCase
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

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState = _downloadState.asStateFlow()

    fun downloadApk(url: String, fileName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadApkUseCase(url, fileName) {
                _downloadState.value = it
            }
        }
    }

    fun cancelDownload() {
        downloadApkUseCase.cancel()
    }
}