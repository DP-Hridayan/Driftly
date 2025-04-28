package `in`.hridayan.driftly.core.domain.model

data class SubjectAttendance(
    val presentCount: Int = 0,
    val absentCount: Int = 0,
    val totalCount: Int = 0
)