package `in`.hridayan.driftly.settingsv2

sealed class PreferenceGroup {
    data class Category(val titleResId: Int, val items: List<PreferenceItem>) : PreferenceGroup()
    data class Items(val items: List<PreferenceItem>) : PreferenceGroup()
}
