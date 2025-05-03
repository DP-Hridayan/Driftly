package `in`.hridayan.driftly.settings.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ds = context.settingsDataStore

    private fun SettingsKeys.toBooleanKey(): Preferences.Key<Boolean> =
        booleanPreferencesKey(this.name)

    fun isEnabled(key: SettingsKeys): Flow<Boolean> {
        val preferencesKey = key.toBooleanKey()
        return ds.data.map { prefs -> prefs[preferencesKey] == true }
    }

    suspend fun toggle(key: SettingsKeys) {
        val preferencesKey = key.toBooleanKey()
        ds.edit { prefs ->
            val current = prefs[preferencesKey] == true
            prefs[preferencesKey] = !current
        }
    }

    private fun SettingsKeys.toIntKey(): Preferences.Key<Int> =
        intPreferencesKey(this.name)

    fun intFlow(key: SettingsKeys, default: Int = 0): Flow<Int> {
        val preferencesKey = key.toIntKey()
        return ds.data
            .map { prefs -> prefs[preferencesKey] ?: default }
    }

    suspend fun getInt(key: SettingsKeys, default: Int = 0): Int {
        val preferencesKey = key.toIntKey()
        return ds.data.first()[preferencesKey] ?: default
    }

    suspend fun setInt(key: SettingsKeys, value: Int) {
        val preferencesKey = key.toIntKey()
        ds.edit { prefs ->
            prefs[preferencesKey] = value
        }
    }
}
