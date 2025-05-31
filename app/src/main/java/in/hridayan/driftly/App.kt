package `in`.hridayan.driftly

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import `in`.hridayan.driftly.core.utils.EncryptionHelper

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        EncryptionHelper.init()
    }
}