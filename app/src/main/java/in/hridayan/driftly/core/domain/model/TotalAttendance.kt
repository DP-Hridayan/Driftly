package `in`.hridayan.driftly.core.domain.model

data class TotalAttendance(
    val totalPresent: Int = 0,
    val totalAbsent: Int = 0,
    val totalCount: Int = 0
)