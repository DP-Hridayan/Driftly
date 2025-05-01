package `in`.hridayan.driftly.settings.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class SettingsDataStore(context: Context) {
    private val ds = context.settingsDataStore

    suspend fun isEnabled(key: String): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        return ds.data.first()[preferencesKey] == true
    }

    suspend fun toggle(key: String) {
        val preferencesKey = booleanPreferencesKey(key)
        ds.edit { prefs ->
            val current = prefs[preferencesKey] == true
            prefs[preferencesKey] = !current
        }
    }
}
