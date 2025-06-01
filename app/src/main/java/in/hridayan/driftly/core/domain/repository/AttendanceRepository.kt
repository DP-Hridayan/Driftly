package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    suspend fun insertAttendance(attendance: AttendanceEntity)
    suspend fun insertAllAttendances(attendances: List<AttendanceEntity>)
    suspend fun updateAttendance(attendance: AttendanceEntity)
    suspend fun deleteAttendance(subjectId: Int, date: String)
    suspend fun deleteAllAttendances()
    suspend fun deleteAllAttendanceForSubject(subjectId: Int)
    suspend fun getAllAttendancesOnce(): List<AttendanceEntity>
    suspend fun hasUnmarkedAttendanceForDate(date: String): Boolean
    fun getAttendanceForSubject(subjectId: Int): Flow<List<AttendanceEntity>>
    fun getTotalCountByStatus(status: AttendanceStatus): Flow<Int>
    fun getCountBySubjectAndStatus(subjectId: Int, status: AttendanceStatus): Flow<Int>
    fun getPresentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
    fun getAbsentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
    fun getTotalCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int>
}
