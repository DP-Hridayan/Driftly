package `in`.hridayan.driftly.settings.data.local.model

sealed class PreferenceGroup {
    data class Category(val categoryNameResId: Int, val items: List<PreferenceItem>) : PreferenceGroup()
    data class Items(val items: List<PreferenceItem>) : PreferenceGroup()
}