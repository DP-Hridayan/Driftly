package `in`.hridayan.driftly.calender.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _subjectId = MutableStateFlow<Int?>(null)

    private val _selectedMonthYear = mutableStateOf(YearMonth.now())
    val selectedMonthYear: State<YearMonth> = _selectedMonthYear

    @OptIn(ExperimentalCoroutinesApi::class)
    val attendanceList: StateFlow<List<AttendanceEntity>> =
        _subjectId
            .filterNotNull()
            .flatMapLatest { attendanceRepository.getAttendanceForSubject(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        val subjectId = savedStateHandle.get<Int>("subjectId")
        _subjectId.value = subjectId
    }

    fun upsert(att: AttendanceEntity, newStatus: AttendanceStatus) {
        viewModelScope.launch {
            attendanceRepository.insertAttendance(att.copy(status = newStatus))
        }
    }

    fun clear(subjectId: Int, date: String) {
        viewModelScope.launch { attendanceRepository.deleteAttendance(subjectId, date) }
    }

    fun getMonthlyAttendanceCounts(
        subjectId: Int,
        year: Int,
        month: Int
    ): Flow<SubjectAttendance> {
        val presentFlow = attendanceRepository.getPresentCountForMonth(subjectId, year, month)
        val absentFlow = attendanceRepository.getAbsentCountForMonth(subjectId, year, month)
        val totalFlow = attendanceRepository.getTotalCountForMonth(subjectId, year, month)

        return combine(presentFlow, absentFlow, totalFlow) { present, absent, total ->
            SubjectAttendance(
                presentCount = present,
                absentCount = absent,
                totalCount = total
            )
        }
    }

    fun updateMonthYear(year: Int, month: Int) {
        _selectedMonthYear.value = YearMonth.of(year, month)
    }
}