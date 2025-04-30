package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.data.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {
    fun getAllSubjects(): Flow<List<SubjectEntity>>
    suspend fun insertSubject(subject: SubjectEntity)
    suspend fun deleteSubject(subjectId: Int)
    fun getSubjectCount(): Flow<Int>
    fun isSubjectExists(subject: String): Flow<Boolean>
}