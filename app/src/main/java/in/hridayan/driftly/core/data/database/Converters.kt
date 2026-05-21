package `in`.hridayan.driftly.core.data.database

import androidx.room.TypeConverter
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus
import `in`.hridayan.driftly.core.domain.model.SubjectClassType

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromStatus(value: AttendanceStatus): String = value.name

    @TypeConverter
    @JvmStatic
    fun toStatus(value: String): AttendanceStatus = AttendanceStatus.valueOf(value)

    @TypeConverter
    @JvmStatic
    fun fromSubjectClassType(value: SubjectClassType): String {
        return value.name
    }

    @TypeConverter
    @JvmStatic
    fun toSubjectClassType(value: String): SubjectClassType {
        return SubjectClassType.valueOf(value)
    }
}
