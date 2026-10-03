package `in`.hridayan.driftly.core.domain.repository

import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import kotlinx.coroutines.flow.Flow

interface SubjectNoteRepository {
    suspend fun insertNote(note: SubjectNoteEntity): Long
    suspend fun insertAllNotes(notes: List<SubjectNoteEntity>)
    suspend fun updateNote(note: SubjectNoteEntity)
    suspend fun deleteNote(note: SubjectNoteEntity)
    suspend fun deleteNoteById(id: Int)
    suspend fun deleteAllNotesForSubject(subjectId: Int)
    fun getNotesForSubject(subjectId: Int): Flow<List<SubjectNoteEntity>>
    fun getNotesForDate(subjectId: Int, date: String): Flow<List<SubjectNoteEntity>>
    fun getNotesForMonth(subjectId: Int, year: Int, month: Int): Flow<List<SubjectNoteEntity>>
    fun getDatesWithNotes(subjectId: Int): Flow<List<String>>
    suspend fun getAllNotesOnce(): List<SubjectNoteEntity>
}
