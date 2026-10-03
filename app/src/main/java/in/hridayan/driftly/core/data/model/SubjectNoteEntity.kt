package `in`.hridayan.driftly.core.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "subject_notes",
    indices = [
        Index(value = ["subjectId", "date"]),
        Index(value = ["subjectId"])
    ]
)
data class SubjectNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: Int,
    val date: String,
    val note: String,
    val createdAt: Long = System.currentTimeMillis()
)
