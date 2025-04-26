package `in`.hridayan.driftly.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.data.model.AttendanceStatus
import `in`.hridayan.driftly.core.data.model.AttendanceSummary
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
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
    init {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    private val _subject = MutableStateFlow("")
    val subject: StateFlow<String> = _subject

    private val _subjectError = MutableStateFlow(false)
    val subjectError: StateFlow<Boolean> = _subjectError


    private val _attendanceSummary = MutableStateFlow(AttendanceSummary())
    val attendanceSummary: StateFlow<AttendanceSummary> = _attendanceSummary

    fun calculateAttendanceSummary() {
        viewModelScope.launch {
            val summary = withContext (Dispatchers.IO) {
                val allAttendances = attendanceRepository.getAllAttendances()

                val totalPresent = allAttendances.count { it.status == AttendanceStatus.PRESENT }
                val totalAbsent = allAttendances.count { it.status == AttendanceStatus.ABSENT }
                val totalCount = allAttendances.size

                AttendanceSummary(
                    totalPresent = totalPresent,
                    totalAbsent = totalAbsent,
                    totalCount = totalCount
                )
            }
            _attendanceSummary.value = summary
        }
    }

    fun onSubjectChange(newValue: String) {
        _subject.value = newValue
        _subjectError.value = false
    }

    val subjectList: Flow<List<SubjectEntity>> = subjectRepository.getAllSubjects().stateIn(
        viewModelScope,
        SharingStarted.Companion.Lazily, emptyList()
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

    fun getAttendanceCounts(subjectId: Int): Flow<AttendanceCounts> {
        val presentFlow = attendanceRepository.getPresentCountForSubject(subjectId)
        val absentFlow = attendanceRepository.getAbsentCountForSubject(subjectId)
        val totalFlow = attendanceRepository.getTotalCountForSubject(subjectId)

        return combine(presentFlow, absentFlow, totalFlow) { present, absent, total ->
            AttendanceCounts(
                presentCount = present,
                absentCount = absent,
                totalCount = total
            )
        }
    }

}

data class AttendanceCounts(
    val presentCount: Int = 0,
    val absentCount: Int = 0,
    val totalCount: Int = 0
)
