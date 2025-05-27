package `in`.hridayan.driftly.settingsv2

data class PreferenceCategory(
    val titleResId: Int,
    val items: List<PreferenceItem>
)