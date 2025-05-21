package `in`.hridayan.driftly.settings.presentation.page.autoupdate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.domain.model.UpdateResult
import `in`.hridayan.driftly.settings.domain.usecase.CheckUpdateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoUpdateViewModel @Inject constructor(
    private val checkUpdateUseCase: CheckUpdateUseCase
) : ViewModel() {
    private val _updateEvents = MutableSharedFlow<UpdateResult>()
    val updateEvents = _updateEvents.asSharedFlow()

    fun checkForUpdates(currentVersion: String) {
        viewModelScope.launch {
            val result = checkUpdateUseCase(currentVersion)
            _updateEvents.emit(result)
        }
    }

}