package `in`.hridayan.driftly.settings.data.model

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import `in`.hridayan.driftly.settings.data.SettingsKeys
import `in`.hridayan.driftly.settings.data.settingsDataStore
import kotlinx.coroutines.flow.Flow
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
        val default = key.default as? Boolean == true

        return ds.data.map { prefs ->
            if (!prefs.contains(preferencesKey)) {
                runCatching {
                    context.settingsDataStore.edit { it[preferencesKey] = default }
                }
            }
            prefs[preferencesKey] ?: default
        }
    }

    suspend fun setBoolean(key: SettingsKeys, value: Boolean) {
        val preferencesKey = key.toBooleanKey()
        ds.edit { prefs ->
            prefs[preferencesKey] = value
        }
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

    fun intFlow(key: SettingsKeys): Flow<Int> {
        val preferencesKey = key.toIntKey()
        val default = key.default as? Int ?: 0
        return ds.data
            .map { prefs -> prefs[preferencesKey] ?: default }
    }

    suspend fun setInt(key: SettingsKeys, value: Int) {
        val preferencesKey = key.toIntKey()
        ds.edit { prefs ->
            prefs[preferencesKey] = value
        }
    }
}