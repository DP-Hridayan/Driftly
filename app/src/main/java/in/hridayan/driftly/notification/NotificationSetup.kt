package `in`.hridayan.driftly.notification

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.notification.helper.NotificationHelper

object NotificationSetup {
    const val ATTENDANCE_CHANNEL_ID = "attendance_channel"
    const val UPDATE_CHANNEL_ID = "update_channel"

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showAttendanceReminderNotification(context: Context) {
        NotificationHelper.showNotification(
            context = context,
            channelId = ATTENDANCE_CHANNEL_ID,
            channelName = context.getString(R.string.attendance),
            channelDescription = context.getString(R.string.daily_reminder_to_mark_attendance),
            notificationId = 1001,
            title = context.getString(R.string.mark_your_attendance),
            message = context.getString(R.string.des_mark_your_attendance),
            smallIconResId = R.drawable.ic_check_circle,
        )
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showMissedAttendanceNotification(context: Context) {
        NotificationHelper.showNotification(
            context = context,
            channelId = ATTENDANCE_CHANNEL_ID,
            channelName = context.getString(R.string.attendance),
            channelDescription = context.getString(R.string.daily_reminder_to_mark_attendance),
            notificationId = 1002,
            title = context.getString(R.string.mark_your_attendance),
            message = context.getString(R.string.des_attendance_missed),
            smallIconResId = R.drawable.ic_check_circle,
        )
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showUpdateAvailableNotification(context: Context) {
        NotificationHelper.showNotification(
            context = context,
            channelId = UPDATE_CHANNEL_ID,
            channelName = context.getString(R.string.updates),
            channelDescription = context.getString(R.string.des_notify_update_available),
            notificationId = 1003,
            title = context.getString(R.string.update_available),
            message = context.getString(R.string.des_update_available),
            smallIconResId = R.drawable.ic_release_alert,
        )
    }
}
