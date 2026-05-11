package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.domain.model.SubjectClassType
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    fun getAllSubjects(): Flow<List<SubjectEntity>>
    suspend fun getAllSubjectsOnce(): List<SubjectEntity>
    fun getSubjectById(id: Int): Flow<SubjectEntity>
    suspend fun getSubjectByIdOnce(id: Int) : SubjectEntity
    suspend fun insertSubject(subject: SubjectEntity)
    suspend fun insertAllSubjects(subjects: List<SubjectEntity>)
    suspend fun updateSubject(
        subjectId: Int,
        newName: String,
        newRoom: String?,
        newClassType: SubjectClassType
    )

    suspend fun deleteSubject(subjectId: Int)
    suspend fun deleteAllSubjects()
    fun getSubjectCount(): Flow<Int>
    fun isSubjectExists(subject: String, classType: SubjectClassType): Flow<Boolean>
    suspend fun updateSavedMonthYear(subjectId: Int, month: Int, year: Int)
}