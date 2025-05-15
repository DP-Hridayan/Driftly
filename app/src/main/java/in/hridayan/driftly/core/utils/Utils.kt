package `in`.hridayan.driftly.core.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun openUrl(url: String, context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())

        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent)
    } catch (ignored: ActivityNotFoundException) {
    }
}