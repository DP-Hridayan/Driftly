package `in`.hridayan.driftly.settings.data.local

import androidx.appcompat.app.AppCompatDelegate
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.common.constants.GithubReleaseType
import `in`.hridayan.driftly.settings.data.local.model.RadioButtonOptions

class RadioGroupOptionsProvider {
    companion object {
        val darkModeOptions: List<RadioButtonOptions> = listOf(
            RadioButtonOptions(
                value = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                labelResId = R.string.system
            ),
            RadioButtonOptions(
                value = AppCompatDelegate.MODE_NIGHT_NO,
                labelResId = R.string.off
            ),
            RadioButtonOptions(
                value = AppCompatDelegate.MODE_NIGHT_YES,
                labelResId = R.string.on
            )
        )

        val updateChannelOptions: List<RadioButtonOptions> = listOf(
            RadioButtonOptions(
                value = GithubReleaseType.STABLE,
                labelResId = R.string.stable
            ),
            RadioButtonOptions(
                value = GithubReleaseType.PRE_RELEASE,
                labelResId = R.string.pre_release
            ),
        )
    }
}