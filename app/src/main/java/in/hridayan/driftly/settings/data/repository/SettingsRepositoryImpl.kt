package `in`.hridayan.driftly.settings.data.repository

import `in`.hridayan.driftly.core.utils.constants.SettingsKeys
import `in`.hridayan.driftly.settings.data.SettingsDataStore
import `in`.hridayan.driftly.settings.data.SettingsProvider
import `in`.hridayan.driftly.settings.domain.model.SettingsItem
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override suspend fun isSettingEnabled(key: SettingsKeys): Flow<Boolean> =
        dataStore.isEnabled(key)

    override suspend fun toggleSetting(key: SettingsKeys) = dataStore.toggle(key)

    override suspend fun getSettingsPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.settingsPageList.map {
            it to dataStore.isEnabled(it.key)
        }
    }

    override suspend fun getLookAndFeelPageList(): List<Pair<SettingsItem, Flow<Boolean>>> {
        return SettingsProvider.lookAndFeelPageList.map {
            it to dataStore.isEnabled(it.key)
        }
    }
}
