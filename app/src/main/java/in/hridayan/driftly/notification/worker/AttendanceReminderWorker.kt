package `in`.hridayan.driftly.notification.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.di.entry.WorkerEntryPoint
import `in`.hridayan.driftly.core.domain.repository.SubjectRepository
import `in`.hridayan.driftly.notification.NotificationSetup
import java.util.Calendar

class AttendanceReminderWorker(
    @param:ApplicationContext private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val subjectRepository: SubjectRepository by lazy {
        EntryPointAccessors.fromApplication(
            applicationContext,
            WorkerEntryPoint::class.java
        ).subjectRepository()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return try {
            val subjects = subjectRepository.getAllSubjectsOnce()
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

            val hasClassToday = subjects.any { subject ->
                val days = subject.daysOfWeek?.split(",")?.mapNotNull { it.trim().toIntOrNull() }
                days == null || days.isEmpty() || days.contains(today)
            }

            if (hasClassToday) {
                NotificationSetup.showAttendanceReminderNotification(applicationContext)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
