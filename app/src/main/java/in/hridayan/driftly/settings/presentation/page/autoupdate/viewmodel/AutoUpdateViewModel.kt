package `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.domain.usecase.CheckUpdateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoUpdateViewModel @Inject constructor(
    private val checkUpdateUseCase: CheckUpdateUseCase
) : ViewModel() {
    private val _isUpdateAvailable = MutableStateFlow<Boolean?>(null)
    val isUpdateAvailable: StateFlow<Boolean?> = _isUpdateAvailable

    fun checkUpdate(currentVersion: String) {
        viewModelScope.launch {
            _isUpdateAvailable.value = checkUpdateUseCase(currentVersion)
        }
    }
}