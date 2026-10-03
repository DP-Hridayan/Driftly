package `in`.hridayan.driftly.core.data.repository

import `in`.hridayan.driftly.core.data.database.SubjectNoteDao
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import `in`.hridayan.driftly.core.domain.repository.SubjectNoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectNoteRepositoryImpl @Inject constructor(
    private val dao: SubjectNoteDao
) : SubjectNoteRepository {

    override suspend fun insertNote(note: SubjectNoteEntity): Long =
        dao.insertNote(note)

    override suspend fun insertAllNotes(notes: List<SubjectNoteEntity>) {
        dao.insertAllNotes(notes)
    }

    override suspend fun updateNote(note: SubjectNoteEntity) {
        dao.updateNote(note)
    }

    override suspend fun deleteNote(note: SubjectNoteEntity) {
        dao.deleteNote(note)
    }

    override suspend fun deleteNoteById(id: Int) {
        dao.deleteNoteById(id)
    }

    override suspend fun deleteAllNotesForSubject(subjectId: Int) {
        dao.deleteAllNotesForSubject(subjectId)
    }

    override fun getNotesForSubject(subjectId: Int): Flow<List<SubjectNoteEntity>> =
        dao.getNotesForSubject(subjectId)

    override fun getNotesForDate(subjectId: Int, date: String): Flow<List<SubjectNoteEntity>> =
        dao.getNotesForDate(subjectId, date)

    override fun getNotesForMonth(subjectId: Int, year: Int, month: Int): Flow<List<SubjectNoteEntity>> =
        dao.getNotesForMonth(subjectId, year, month)

    override fun getDatesWithNotes(subjectId: Int): Flow<List<String>> =
        dao.getDatesWithNotes(subjectId)

    override suspend fun getAllNotesOnce(): List<SubjectNoteEntity> =
        dao.getAllNotesOnce()
}
