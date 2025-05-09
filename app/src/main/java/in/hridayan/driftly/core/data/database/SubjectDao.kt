package `in`.hridayan.driftly.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects order by subject ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Query("UPDATE subjects SET subject = :newName WHERE id = :subjectId")
    suspend fun updateSubject(subjectId: Int, newName: String)

    @Query("DELETE FROM subjects WHERE id = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("SELECT COUNT(*) FROM subjects")
    fun getSubjectCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT * FROM subjects WHERE subject = :subject)")
    fun isSubjectExists(subject: String): Flow<Boolean>
}