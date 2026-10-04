package `in`.hridayan.driftly.settings.domain.model

import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import `in`.hridayan.driftly.core.data.model.SubjectNoteEntity
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val settings: Map<String, String?>? = null,
    val attendance: List<AttendanceEntity>? = null,
    val subjects: List<SubjectEntity>? = null,
    val notes: List<SubjectNoteEntity>? = null,
    val backupTime: String
)
