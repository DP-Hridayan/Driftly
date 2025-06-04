package `in`.hridayan.driftly.home.presentation.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.domain.model.SubjectError
import `in`.hridayan.driftly.core.domain.model.TotalAttendance
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import `in`.hridayan.driftly.core.utils.createAppNotificationSettingsIntent
import `in`.hridayan.driftly.settings.presentation.event.SettingsUiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<SettingsUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _subject = MutableStateFlow("")
    val subject: StateFlow<String> = _subject

    private val _subjectError = MutableStateFlow<SubjectError>(SubjectError.None)
    val subjectError: StateFlow<SubjectError> = _subjectError

    fun setSubjectNamePlaceholder(value: String) {
        if (_subject.value.isBlank()) {
            _subject.value = value
        }
    }

    fun onSubjectChange(newValue: String) {
        _subject.value = newValue
        _subjectError.value = SubjectError.None
    }

    val subjectList: Flow<List<SubjectEntity>> = subjectRepository.getAllSubjects().stateIn(
        viewModelScope,
        SharingStarted.Eagerly, emptyList()
    )

    fun addSubject(onSuccess: () -> Unit) {
        val isSubjectInvalid = _subject.value.trim().isBlank()
        if (isSubjectInvalid) {
            _subjectError.value = SubjectError.Empty
            return
        }

        viewModelScope.launch {
            val isSubjectExists = subjectRepository.isSubjectExists(_subject.value.trim()).first()
            if (isSubjectExists) {
                _subjectError.value = SubjectError.AlreadyExists
            } else {
                subjectRepository.insertSubject(
                    SubjectEntity(
                        subject = _subject.value.trim()
                    )
                )
                _subject.value = ""
                onSuccess()
            }
        }
    }

    fun resetInputFields() {
        _subject.value = ""
        _subjectError.value = SubjectError.None
    }

    fun updateSubject(subjectId: Int, onSuccess: () -> Unit) {
        val isSubjectInvalid = _subject.value.trim().isBlank()
        if (isSubjectInvalid) {
            _subjectError.value = SubjectError.Empty
            return
        }

        viewModelScope.launch {
            val isSubjectExists = subjectRepository.isSubjectExists(_subject.value.trim()).first()
            if (isSubjectExists) {
                _subjectError.value = SubjectError.AlreadyExists
            } else {
                subjectRepository.updateSubject(
                    subjectId = subjectId,
                    newName = _subject.value.trim()
                )
                _subject.value = ""
                onSuccess()
            }
        }
    }

    fun deleteSubject(subjectId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            subjectRepository.deleteSubject(subjectId)
            onSuccess()
        }
    }

    val subjectCount: Flow<Int> = subjectRepository.getSubjectCount()

    fun deleteAllAttendanceForSubject(subjectId: Int) {
        viewModelScope.launch {
            attendanceRepository.deleteAllAttendanceForSubject(subjectId)
        }
    }

    fun getSubjectAttendanceCounts(subjectId: Int): Flow<SubjectAttendance> {
        val presentFlow = attendanceRepository.getCountBySubjectAndStatus(
            subjectId,
            AttendanceStatus.PRESENT
        )
        val absentFlow =
            attendanceRepository.getCountBySubjectAndStatus(subjectId, AttendanceStatus.ABSENT)

        return combine(presentFlow, absentFlow) { present, absent ->
            SubjectAttendance(
                presentCount = present,
                absentCount = absent,
                totalCount = present + absent
            )
        }
    }

    fun getTotalAttendanceCounts(): Flow<TotalAttendance> {
        val presentFlow = attendanceRepository.getTotalCountByStatus(AttendanceStatus.PRESENT)
        val absentFlow = attendanceRepository.getTotalCountByStatus(AttendanceStatus.ABSENT)

        return combine(presentFlow, absentFlow) { present, absent ->
            TotalAttendance(
                totalPresent = present,
                totalAbsent = absent,
                totalCount = present + absent
            )
        }
    }

    fun requestNotificationPermission() {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                _uiEvent.emit(SettingsUiEvent.RequestPermission(android.Manifest.permission.POST_NOTIFICATIONS))
            } else {
                val intent = createAppNotificationSettingsIntent(context)
                _uiEvent.emit(SettingsUiEvent.LaunchIntent(intent))
            }
        }
    }
}


