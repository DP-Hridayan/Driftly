package `in`.hridayan.driftly.settings.presentation.page.backup.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.domain.repository.BackupAndRestoreRepository
import javax.inject.Inject

@HiltViewModel
class BackupAndRestoreViewModel @Inject constructor(
    private val backupAndRestoreRepository: BackupAndRestoreRepository
) : ViewModel() {

}