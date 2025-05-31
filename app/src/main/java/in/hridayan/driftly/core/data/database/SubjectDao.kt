package `in`.hridayan.driftly.core.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects order by subject ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subjects WHERE id = :id")
    fun getSubjectById(id: Int): Flow<SubjectEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSubjects(subjects: List<SubjectEntity>)

    @Query("UPDATE subjects SET subject = :newName WHERE id = :subjectId")
    suspend fun updateSubject(subjectId: Int, newName: String)

    @Query("DELETE FROM subjects WHERE id = :subjectId")
    suspend fun deleteSubject(subjectId: Int)

    @Query("DELETE FROM subjects")
    suspend fun deleteAllSubjects()

    @Query("SELECT COUNT(*) FROM subjects")
    fun getSubjectCount(): Flow<Int>

    @Query("SELECT * FROM subjects ORDER BY subject ASC")
    suspend fun getAllSubjectsOnce(): List<SubjectEntity>

    @Query("SELECT EXISTS(SELECT * FROM subjects WHERE subject = :subject)")
    fun isSubjectExists(subject: String): Flow<Boolean>

    @Query("UPDATE subjects SET savedMonth = :month, savedYear = :year WHERE id = :subjectId")
    suspend fun updateSavedMonthYear(subjectId: Int, month: Int, year: Int)

}