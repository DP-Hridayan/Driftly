package `in`.hridayan.driftly.core.data.database

import androidx.room.TypeConverter
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromStatus(value: AttendanceStatus): String = value.name

    @TypeConverter
    @JvmStatic
    fun toStatus(value: String): AttendanceStatus = AttendanceStatus.valueOf(value)
}
