package `in`.hridayan.driftly.core.data.repository

import `in`.hridayan.driftly.core.data.database.SubjectDao
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectRepository {
    override fun getAllSubjects(): Flow<List<SubjectEntity>> {
        return subjectDao.getAllSubjects()
    }

    override fun getSubjectById(id: Int): Flow<SubjectEntity> {
        return subjectDao.getSubjectById(id)
    }

    override suspend fun insertSubject(subject: SubjectEntity) {
        subjectDao.insertSubject(subject)
    }

    override suspend fun insertAllSubjects(subjects: List<SubjectEntity>) {
        subjectDao.insertAllSubjects(subjects)
    }

    override suspend fun updateSubject(subjectId: Int, newName: String) {
        subjectDao.updateSubject(subjectId, newName)
    }

    override suspend fun deleteSubject(subjectId: Int) {
        subjectDao.deleteSubject(subjectId)
    }

    override suspend fun deleteAllSubjects() {
        subjectDao.deleteAllSubjects()
    }

    override suspend fun getAllSubjectsOnce(): List<SubjectEntity> =
        subjectDao.getAllSubjectsOnce()

    override fun getSubjectCount(): Flow<Int> =
        subjectDao.getSubjectCount()

    override fun isSubjectExists(subject: String): Flow<Boolean> =
        subjectDao.isSubjectExists(subject)

    override suspend fun updateSavedMonthYear(subjectId: Int, month: Int, year: Int) {
        subjectDao.updateSavedMonthYear(subjectId, month, year)
    }
}