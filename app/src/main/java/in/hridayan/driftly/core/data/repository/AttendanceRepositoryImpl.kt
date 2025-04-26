package `in`.hridayan.driftly.core.data.repository

import `in`.hridayan.driftly.core.data.database.AttendanceDao
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val dao: AttendanceDao
) : AttendanceRepository {

    override suspend fun insertAttendance(att: AttendanceEntity) {
        dao.insertAttendance(att)
    }

    override suspend fun updateAttendance(att: AttendanceEntity) {
        dao.updateAttendance(att)
    }

    override suspend fun deleteAttendance(subjectId: Int, date: String) {
        dao.deleteBySubjectAndDate(subjectId, date)
    }

    override fun getAttendanceForSubject(subjectId: Int): Flow<List<AttendanceEntity>> =
        dao.getAttendanceForSubjectFlow(subjectId)

    override fun getPresentCountForSubject(subjectId: Int): Flow<Int> =
        dao.getPresentCount(subjectId)

    override fun getAbsentCountForSubject(subjectId: Int): Flow<Int> =
        dao.getAbsentCount(subjectId)

    override fun getTotalCountForSubject(subjectId: Int): Flow<Int> =
        dao.getTotalCount(subjectId)

    override fun getPresentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getPresentCountForMonth(subjectId, year, month)

    override fun getAbsentCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getAbsentCountForMonth(subjectId, year, month)

    override fun getTotalCountForMonth(subjectId: Int, year: Int, month: Int): Flow<Int> =
        dao.getTotalCountForMonth(subjectId, year, month)
}
