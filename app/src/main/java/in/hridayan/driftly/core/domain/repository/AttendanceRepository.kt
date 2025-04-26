package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    suspend fun insertAttendance(attendance: AttendanceEntity)
    suspend fun updateAttendance(attendance: AttendanceEntity)
    suspend fun deleteAttendance(subjectId: Int, date: String)
    fun getAttendanceForSubject(subjectId: Int): Flow<List<AttendanceEntity>>
    fun getPresentCountForSubject(subjectId: Int): Flow<Int>
    fun getAbsentCountForSubject(subjectId: Int): Flow<Int>
    fun getTotalCountForSubject(subjectId: Int): Flow<Int>
    fun getPresentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
    fun getAbsentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
    fun getTotalCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
}
