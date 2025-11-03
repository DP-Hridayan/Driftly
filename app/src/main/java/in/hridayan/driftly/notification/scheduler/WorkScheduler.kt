package `in`.hridayan.driftly.notification.scheduler

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import `in`.hridayan.driftly.core.domain.model.NotificationTags
import `in`.hridayan.driftly.notification.worker.AttendanceReminderWorker
import `in`.hridayan.driftly.notification.worker.MissedAttendanceAlertWorker
import `in`.hridayan.driftly.notification.worker.UpdateCheckWorker
import java.time.Duration
import java.util.Calendar

object WorkScheduler {

    fun scheduleAttendanceReminder(
        context: Context,
        hour: Int = 10,
        minute: Int = 0
    ) {
        scheduleWork(
            workerClass = AttendanceReminderWorker::class.java,
            tag = NotificationTags.REMINDER_TO_MARK_ATTENDANCE,
            context = context,
            hour = hour,
            minute = minute
        )
    }

    fun scheduleMissedAttendanceAlert(
        context: Context,
        hour: Int = 16,
        minute: Int = 0
    ) {
        scheduleWork(
            workerClass = MissedAttendanceAlertWorker::class.java,
            tag = NotificationTags.NOTIFY_WHEN_MISSED_ATTENDANCE,
            context = context,
            hour = hour,
            minute = minute
        )
    }

    fun scheduleDailyUpdateCheck(
        context: Context,
        hour: Int = 18,
        minute: Int = 0
    ) {
        scheduleWork(
            workerClass = UpdateCheckWorker::class.java,
            tag = NotificationTags.NOTIFY_WHEN_UPDATE_AVAILABLE,
            context = context,
            hour = hour,
            minute = minute
        )
    }

    private inline fun <reified T : ListenableWorker> scheduleWork(
        workerClass: Class<T>,
        tag: String,
        context: Context,
        hour: Int,
        minute: Int,
        inputData: Data = Data.Builder().build()
    ) {
        val now = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
        }

        val delay = scheduledTime.timeInMillis - now.timeInMillis

        val request = PeriodicWorkRequestBuilder<T>(
            Duration.ofDays(1)
        )
            .setInitialDelay(Duration.ofMillis(delay))
            .addTag(tag)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            tag,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    fun cancelNotificationWork(context: Context, uniqueTag: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag(uniqueTag)
    }

    fun cancelAllNotificationWork(context: Context) {
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(NotificationTags.REMINDER_TO_MARK_ATTENDANCE)
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(NotificationTags.NOTIFY_WHEN_MISSED_ATTENDANCE)
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(NotificationTags.NOTIFY_WHEN_UPDATE_AVAILABLE)
    }
}
