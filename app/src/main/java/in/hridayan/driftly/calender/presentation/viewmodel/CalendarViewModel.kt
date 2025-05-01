package `in`.hridayan.driftly.calender.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.StreakType
import `in`.hridayan.driftly.core.domain.model.SubjectAttendance
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _subjectId = MutableStateFlow<Int?>(null)

    private val _selectedMonthYear = mutableStateOf(YearMonth.now())
    val selectedMonthYear: State<YearMonth> = _selectedMonthYear

    private val _markedDates = MutableStateFlow<Map<LocalDate, AttendanceStatus>>(emptyMap())
    val markedDatesFlow: StateFlow<Map<LocalDate, AttendanceStatus>> = _markedDates

    private val _streakMap = MutableStateFlow<Map<LocalDate, StreakType>>(emptyMap())
    val streakMapFlow: StateFlow<Map<LocalDate, StreakType>> = _streakMap

    init {
        savedStateHandle.get<Int>("subjectId")?.also { id ->
            _subjectId.value = id
            loadAttendanceData(id)
        }
    }

    fun updateMonthYear(year: Int, month: Int) {
        _selectedMonthYear.value = YearMonth.of(year, month)
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
        subjectId: Int, year: Int, month: Int
    ): Flow<SubjectAttendance> {
        val presentFlow = attendanceRepository.getPresentCountForMonth(subjectId, year, month)
        val absentFlow  = attendanceRepository.getAbsentCountForMonth(subjectId, year, month)
        val totalFlow   = attendanceRepository.getTotalCountForMonth(subjectId, year, month)
        return combine(presentFlow, absentFlow, totalFlow) { p, a, t ->
            SubjectAttendance(p, a, t)
        }
    }

    private fun loadAttendanceData(subjectId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            attendanceRepository.getAttendanceForSubject(subjectId)
                .collect { list ->
                    // rebuild markedDates map
                    val newMap = list
                        .mapNotNull { e ->
                            if (e.status == AttendanceStatus.UNMARKED) null
                            else LocalDate.parse(e.date) to e.status
                        }
                        .toMap()
                    _markedDates.value = newMap

                    _streakMap.value = calculateStreaks(newMap)
                }
        }
    }

    fun updateDate(date: LocalDate, status: AttendanceStatus) {
        val m = _markedDates.value.toMutableMap()
        if (status == AttendanceStatus.UNMARKED) m.remove(date)
        else m[date] = status
        _markedDates.value = m

        _streakMap.value = calculateStreaks(m)
    }

    private fun calculateStreaks(
        marked: Map<LocalDate, AttendanceStatus>
    ): Map<LocalDate, StreakType> {
        val streaks = mutableMapOf<LocalDate, StreakType>()

        marked.entries.groupBy({ it.value }, { it.key }).forEach { (status, dates) ->
            if (status == AttendanceStatus.UNMARKED) return@forEach

            val sorted = dates.sorted()
            var i = 0
            while (i < sorted.size) {
                var end = i
                while (end+1 < sorted.size && sorted[end+1] == sorted[end].plusDays(1)) {
                    end++
                }
                val len = end - i + 1
                for (j in i..end) {
                    val d = sorted[j]
                    streaks[d] = when {
                        len < 3       -> StreakType.NONE
                        j == i        -> StreakType.START
                        j == end      -> StreakType.END
                        else          -> StreakType.MIDDLE
                    }
                }
                i = end + 1
            }
        }
        return streaks
    }
}
