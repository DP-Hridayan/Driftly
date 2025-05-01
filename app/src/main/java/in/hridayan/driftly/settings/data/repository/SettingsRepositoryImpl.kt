package `in`.hridayan.driftly.settings.data.repository

import `in`.hridayan.driftly.settings.data.SettingsDataStore
import `in`.hridayan.driftly.settings.data.SettingsProvider
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun isSettingEnabled(key: String): Boolean = dataStore.isEnabled(key)

    override suspend fun toggleSetting(key: String) = dataStore.toggle(key)

    override suspend fun getAllSettings(): List<Pair<SettingsItem, Boolean>> {
        return SettingsProvider.settings.map {
            it to dataStore.isEnabled(it.key)
        }
    }
}
