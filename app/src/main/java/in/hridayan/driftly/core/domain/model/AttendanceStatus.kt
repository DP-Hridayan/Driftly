package `in`.hridayan.driftly.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class AttendanceStatus {
    PRESENT, ABSENT, UNMARKED
}