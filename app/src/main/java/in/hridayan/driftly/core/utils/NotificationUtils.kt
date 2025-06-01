package `in`.hridayan.driftly.core.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

fun isNotificationPermissionGranted(context: Context): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        return hasPermission && NotificationManagerCompat.from(context).areNotificationsEnabled()
    } else {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}

fun createAppNotificationSettingsIntent(context: Context): Intent {
    return Intent().apply {
        action = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}

