package `in`.hridayan.driftly.settings.domain.model


import androidx.appcompat.app.AppCompatDelegate
import `in`.hridayan.driftly.R

enum class ThemeOption(val mode: Int, val labelResId: Int) {
    SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,R.string.system),
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO, R.string.off),
    DARK(AppCompatDelegate.MODE_NIGHT_YES, R.string.on);

    companion object {
        fun fromMode(mode: Int) =
            entries.find { it.mode == mode } ?: SYSTEM
    }
}