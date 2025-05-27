package `in`.hridayan.driftly.settingsv2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PreferenceItemView(item: PreferenceItem, modifier: Modifier = Modifier) {
    when (item) {
        is PreferenceItem.BoolPreferenceItem -> BooleanPreferenceItemView(
            item = item,
            modifier = modifier
        )
        //  is PreferenceItem.IntPreferenceItem -> IntPreferenceItemView(item)
        //    is PreferenceItem.StringPreferenceItem -> StringPreferenceItemView(item)
        //   is PreferenceItem.FloatPreferenceItem -> FloatPreferenceItemView(item)
        is PreferenceItem.NullPreferenceItem -> NullPreferenceItemView(
            item = item,
            modifier = modifier
        )

        else -> {}
    }
}