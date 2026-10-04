package `in`.hridayan.driftly.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: SubjectNoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(notes: List<SubjectNoteEntity>)

    @Update
    suspend fun updateNote(note: SubjectNoteEntity)

    @Delete
    suspend fun deleteNote(note: SubjectNoteEntity)

    @Query("DELETE FROM subject_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    @Query("DELETE FROM subject_notes WHERE subjectId = :subjectId")
    suspend fun deleteAllNotesForSubject(subjectId: Int)

    @Query("SELECT * FROM subject_notes WHERE subjectId = :subjectId ORDER BY date DESC, id DESC")
    fun getNotesForSubject(subjectId: Int): Flow<List<SubjectNoteEntity>>

    @Query("SELECT * FROM subject_notes WHERE subjectId = :subjectId AND date = :date ORDER BY id DESC")
    fun getNotesForDate(subjectId: Int, date: String): Flow<List<SubjectNoteEntity>>

    @Query(
        """
        SELECT * FROM subject_notes 
        WHERE subjectId = :subjectId 
        AND strftime('%Y', date) = printf('%04d', :year)
        AND strftime('%m', date) = printf('%02d', :month)
        ORDER BY date DESC, id DESC
        """
    )
    fun getNotesForMonth(subjectId: Int, year: Int, month: Int): Flow<List<SubjectNoteEntity>>

    @Query("SELECT DISTINCT date FROM subject_notes WHERE subjectId = :subjectId")
    fun getDatesWithNotes(subjectId: Int): Flow<List<String>>

    @Query("SELECT * FROM subject_notes ORDER BY date DESC")
    suspend fun getAllNotesOnce(): List<SubjectNoteEntity>
}
