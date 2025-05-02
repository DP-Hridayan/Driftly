package `in`.hridayan.driftly.calender.presentation.components.color

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import `in`.hridayan.driftly.core.domain.model.AttendanceStatus

data class AttendanceColors(
    val background: Color,
    val foreground: Color
)

@Composable
fun getAttendanceColors(status: AttendanceStatus): AttendanceColors {
    return when (status) {
        AttendanceStatus.PRESENT -> AttendanceColors(
            background = MaterialTheme.colorScheme.primary,
            foreground = MaterialTheme.colorScheme.primaryContainer
        )
        AttendanceStatus.ABSENT -> AttendanceColors(
            background = MaterialTheme.colorScheme.error,
            foreground = MaterialTheme.colorScheme.errorContainer
        )
        AttendanceStatus.UNMARKED -> AttendanceColors(
            background = MaterialTheme.colorScheme.surface,
            foreground = MaterialTheme.colorScheme.onSurface
        )
    }
}
