package `in`.hridayan.driftly.notification.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.di.entry.WorkerEntryPoint
import `in`.hridayan.driftly.core.domain.repository.AttendanceRepository
import `in`.hridayan.driftly.notification.NotificationSetup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MissedAttendanceAlertWorker(
    @param:ApplicationContext private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val attendanceRepository: AttendanceRepository by lazy {
        EntryPointAccessors.fromApplication(
            applicationContext,
            WorkerEntryPoint::class.java
        ).attendanceRepository()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return try {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

            val hasUnmarked = attendanceRepository.hasUnmarkedAttendanceForDate(today)

            if (hasUnmarked) {
                NotificationSetup.showMissedAttendanceNotification(applicationContext)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
