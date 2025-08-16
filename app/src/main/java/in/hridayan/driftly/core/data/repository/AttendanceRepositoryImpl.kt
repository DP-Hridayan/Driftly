package `in`.hridayan.driftly.core.data.repository

import `in`.hridayan.driftly.core.data.database.AttendanceDao
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val dao: AttendanceDao
) : AttendanceRepository {

    override suspend fun insertAttendance(attendance: AttendanceEntity) {
        dao.insertAttendance(attendance)
    }

    override suspend fun insertAllAttendances(attendances: List<AttendanceEntity>) {
        dao.insertAllAttendances(attendances)
    }

    override suspend fun updateAttendance(attendance: AttendanceEntity) {
        dao.updateAttendance(attendance)
    }

    override suspend fun deleteAttendance(subjectId: Int, date: String) {
        dao.deleteBySubjectAndDate(subjectId, date)
    }

    override suspend fun deleteAllAttendances() {
        dao.deleteAllAttendances()
    }

    override suspend fun deleteAllAttendanceForSubject(subjectId: Int) {
        dao.deleteAllAttendanceForSubject(subjectId)
    }

    override suspend fun getAllAttendancesOnce(): List<AttendanceEntity> =
        dao.getAllAttendancesOnce()

    override suspend fun hasUnmarkedAttendanceForDate(date: String): Boolean =
        dao.hasUnmarkedAttendanceForDate(date)

    override fun getAttendanceForSubject(subjectId: Int): Flow<List<AttendanceEntity>> =
        dao.getAttendanceForSubjectFlow(subjectId)

    override fun getTotalCountByStatus(status: AttendanceStatus): Flow<Int> =
        dao.getTotalCountByStatus(status)

    override fun getCountBySubjectAndStatus(subjectId: Int, status: AttendanceStatus): Flow<Int> =
        dao.getCountBySubjectAndStatus(subjectId, status)

    override fun getPresentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getPresentCountForMonth(subjectId, year, month)

    override fun getAbsentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getAbsentCountForMonth(subjectId, year, month)

    override fun getTotalCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getTotalCountForMonth(subjectId, year, month)
}
