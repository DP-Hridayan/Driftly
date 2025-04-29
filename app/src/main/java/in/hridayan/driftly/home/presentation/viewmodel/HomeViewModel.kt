package `in`.hridayan.driftly.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.domain.model.TotalAttendance
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {
    private val _subject = MutableStateFlow("")
    val subject: StateFlow<String> = _subject

    private val _subjectError = MutableStateFlow(false)
    val subjectError: StateFlow<Boolean> = _subjectError

    fun onSubjectChange(newValue: String) {
        _subject.value = newValue
        _subjectError.value = false
    }

    val subjectList: Flow<List<SubjectEntity>> = subjectRepository.getAllSubjects().stateIn(
        viewModelScope,
        SharingStarted.Eagerly, emptyList()
    )

    fun addSubject(onSuccess: () -> Unit) {
        val isSubjectValid = _subject.value.trim().isNotBlank()
        _subjectError.value = !isSubjectValid

        if (isSubjectValid) {
            viewModelScope.launch {
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
}


