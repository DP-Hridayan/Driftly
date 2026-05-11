package `in`.hridayan.driftly.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import `in`.hridayan.driftly.core.domain.model.SubjectClassType
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subject: String,
    val savedMonth: Int? = null,
    val savedYear: Int? = null,
    val room: String? = null,
    @ColumnInfo(name = "classType", defaultValue = "NONE")
    val classType: SubjectClassType = SubjectClassType.NONE
)