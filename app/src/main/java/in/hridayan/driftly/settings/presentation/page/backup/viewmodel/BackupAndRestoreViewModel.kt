package `in`.hridayan.driftly.settings.presentation.page.backup.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.domain.model.BackupOption
import `in`.hridayan.driftly.settings.domain.repository.BackupAndRestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupAndRestoreViewModel @Inject constructor(
    private val backupAndRestoreRepository: BackupAndRestoreRepository
) : ViewModel() {
    private var currentBackupOption: BackupOption = BackupOption.SETTINGS_AND_DATABASE

    private val _backupTime = MutableStateFlow<String?>(null)
    val backupTime: StateFlow<String?> = _backupTime

    fun initiateBackup(option: BackupOption) {
        currentBackupOption = option
    }

    fun performBackup(uri: Uri) {
        viewModelScope.launch {
            backupAndRestoreRepository.backupDataToFile(uri, currentBackupOption)
        }
    }

    fun performRestore(uri: Uri) {
        viewModelScope.launch {
            backupAndRestoreRepository.restoreDataFromFile(uri)
        }
    }

    fun loadBackupTime(uri: Uri) {
        viewModelScope.launch {
            val time = backupAndRestoreRepository.getBackupTimeFromFile(uri)
            _backupTime.value = time
        }
    }
}