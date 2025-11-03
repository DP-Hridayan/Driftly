package `in`.hridayan.driftly.settings.domain.model

import androidx.annotation.StringRes
import `in`.hridayan.driftly.settings.domain.model.PreferenceItem

sealed class PreferenceGroup {
    data class Category(
        @param:StringRes val categoryNameResId: Int,
        val items: List<PreferenceItem>
    ) : PreferenceGroup()

    data class Items(val items: List<PreferenceItem>) : PreferenceGroup()
    object HorizontalDivider : PreferenceGroup()
    data class CustomComposable(val label: String) : PreferenceGroup()
}