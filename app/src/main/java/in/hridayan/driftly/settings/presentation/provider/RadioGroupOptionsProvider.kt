package `in`.hridayan.driftly.settings.presentation.provider

import androidx.appcompat.app.AppCompatDelegate
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.domain.model.GithubReleaseType
import `in`.hridayan.driftly.settings.domain.model.CustomFontFamily
import `in`.hridayan.driftly.settings.domain.model.RadioButtonOptions

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

        val fontStyleOptions: List<RadioButtonOptions> = listOf(
            RadioButtonOptions(
                value = CustomFontFamily.SYSTEM_FONT,
                labelResId = R.string.system_font
            ),
            RadioButtonOptions(
                value = CustomFontFamily.ONE_UI_SANS,
                labelResId = R.string.one_ui_sans
            ),
            RadioButtonOptions(
                value = CustomFontFamily.MONOSPACE,
                labelResId = R.string.monospace
            ),
            RadioButtonOptions(
                value = CustomFontFamily.SANS_SERIF,
                labelResId = R.string.sans_serif
            )
        )
    }
}