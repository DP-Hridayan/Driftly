package `in`.hridayan.driftly.core.data.model

import androidx.room.Entity
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "attendance",
    primaryKeys = ["subjectId", "date"]
)
data class AttendanceEntity(
    val subjectId: Int,
    val date: String,
    val status: AttendanceStatus = AttendanceStatus.UNMARKED
)