package `in`.hridayan.driftly.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.TotalAttendance
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {
    private val _subject = MutableStateFlow("")
    val subject: StateFlow<String> = _subject

    private val _subjectError = MutableStateFlow(false)
    val subjectError: StateFlow<Boolean> = _subjectError

    private val _totalAttendance = MutableStateFlow(TotalAttendance())
    val totalAttendance: StateFlow<TotalAttendance> = _totalAttendance

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded

    fun calculateTotalAttendance() {
        viewModelScope.launch(Dispatchers.IO) {
            val summary = withContext (Dispatchers.IO) {
                val allAttendances = attendanceRepository.getAllAttendances()
                val totalPresent = allAttendances.count { it.status == AttendanceStatus.PRESENT }
                val totalAbsent = allAttendances.count { it.status == AttendanceStatus.ABSENT }
                val totalCount = allAttendances.size

                TotalAttendance(
                    totalPresent = totalPresent,
                    totalAbsent = totalAbsent,
                    totalCount = totalCount
                )
            }
            _totalAttendance.value = summary
            _isLoaded.value = true
        }
    }

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

    fun getAttendanceCounts(subjectId: Int): Flow<SubjectAttendance> {
        val presentFlow = attendanceRepository.getPresentCountForSubject(subjectId)
        val absentFlow = attendanceRepository.getAbsentCountForSubject(subjectId)
        val totalFlow = attendanceRepository.getTotalCountForSubject(subjectId)

        return combine(presentFlow, absentFlow, totalFlow) { present, absent, total ->
            SubjectAttendance(
                presentCount = present,
                absentCount = absent,
                totalCount = total
            )
        }
    }
}


