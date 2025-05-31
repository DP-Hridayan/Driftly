package `in`.hridayan.driftly.settings.domain.model

import `in`.hridayan.driftly.core.data.model.AttendanceEntity
import `in`.hridayan.driftly.core.data.model.SubjectEntity
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val settings: Map<String, String?>? = null,
    val attendance: List<AttendanceEntity>? = null,
    val subjects: List<SubjectEntity>? = null,
    val backupTime: String
)
