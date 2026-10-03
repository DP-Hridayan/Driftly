package `in`.hridayan.driftly.core.data.repository

import `in`.hridayan.driftly.core.data.database.SubjectNoteDao
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import `in`.hridayan.driftly.settings.domain.model.BackupData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class SubjectNoteRepositoryTest {

    private class FakeSubjectNoteDao : SubjectNoteDao {
        val notes = mutableListOf<SubjectNoteEntity>()

        override suspend fun insertNote(note: SubjectNoteEntity): Long {
            val generatedId = if (note.id == 0) notes.size + 1 else note.id
            val newNote = note.copy(id = generatedId)
            notes.removeAll { it.id == generatedId }
            notes.add(newNote)
            return generatedId.toLong()
        }

        override suspend fun insertAllNotes(notes: List<SubjectNoteEntity>) {
            this.notes.addAll(notes)
        }

        override suspend fun updateNote(note: SubjectNoteEntity) {
            val index = notes.indexOfFirst { it.id == note.id }
            if (index != -1) {
                notes[index] = note
            }
        }

        override suspend fun deleteNote(note: SubjectNoteEntity) {
            notes.removeAll { it.id == note.id }
        }

        override suspend fun deleteNoteById(id: Int) {
            notes.removeAll { it.id == id }
        }

        override suspend fun deleteAllNotesForSubject(subjectId: Int) {
            notes.removeAll { it.subjectId == subjectId }
        }

        override fun getNotesForSubject(subjectId: Int) =
            flowOf(notes.filter { it.subjectId == subjectId })

        override fun getNotesForDate(subjectId: Int, date: String) =
            flowOf(notes.filter { it.subjectId == subjectId && it.date == date })

        override fun getNotesForMonth(subjectId: Int, year: Int, month: Int) =
            flowOf(notes.filter {
                it.subjectId == subjectId && it.date.startsWith(String.format("%04d-%02d", year, month))
            })

        override fun getDatesWithNotes(subjectId: Int) =
            flowOf(notes.filter { it.subjectId == subjectId }.map { it.date }.distinct())

        override suspend fun getAllNotesOnce(): List<SubjectNoteEntity> = notes.toList()
    }

    @Test
    fun testInsertAndGetNotes() = runBlocking {
        val fakeDao = FakeSubjectNoteDao()
        val repo = SubjectNoteRepositoryImpl(fakeDao)

        val note = SubjectNoteEntity(
            subjectId = 1,
            date = "2026-10-15",
            note = "Covered Chapter 3 on Thermodynamics"
        )
        val id = repo.insertNote(note)
        assertEquals(1L, id)

        val retrieved = repo.getNotesForSubject(1).first()
        assertEquals(1, retrieved.size)
        assertEquals("Covered Chapter 3 on Thermodynamics", retrieved[0].note)
        assertEquals("2026-10-15", retrieved[0].date)
    }

    @Test
    fun testUpdateAndDeleteNotes() = runBlocking {
        val fakeDao = FakeSubjectNoteDao()
        val repo = SubjectNoteRepositoryImpl(fakeDao)

        val note = SubjectNoteEntity(
            subjectId = 2,
            date = "2026-10-20",
            note = "Assignment 1 due next week"
        )
        repo.insertNote(note)

        val noteToUpdate = repo.getNotesForSubject(2).first()[0]
        repo.updateNote(noteToUpdate.copy(note = "Assignment 1 due Friday"))

        val updatedNotes = repo.getNotesForSubject(2).first()
        assertEquals("Assignment 1 due Friday", updatedNotes[0].note)

        repo.deleteNote(updatedNotes[0])
        val afterDelete = repo.getNotesForSubject(2).first()
        assertEquals(0, afterDelete.size)
    }

    @Test
    fun testBackupDataSerializationWithNotes() {
        val json = Json { ignoreUnknownKeys = true }
        val testNote = SubjectNoteEntity(
            id = 10,
            subjectId = 1,
            date = "2026-10-12",
            note = "Important formula E=mc^2"
        )
        val backupData = BackupData(
            settings = null,
            attendance = null,
            subjects = null,
            notes = listOf(testNote),
            backupTime = "12-10-2026 10:00"
        )

        val encoded = json.encodeToString(BackupData.serializer(), backupData)
        val decoded = json.decodeFromString(BackupData.serializer(), encoded)

        assertNotNull(decoded.notes)
        assertEquals(1, decoded.notes?.size)
        assertEquals("Important formula E=mc^2", decoded.notes?.get(0)?.note)
        assertEquals("2026-10-12", decoded.notes?.get(0)?.date)
    }
}
