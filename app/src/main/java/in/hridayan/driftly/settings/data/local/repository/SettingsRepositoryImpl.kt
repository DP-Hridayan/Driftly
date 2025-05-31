package `in`.hridayan.driftly.settings.data.local.repository

import `in`.hridayan.driftly.settings.data.local.SettingsKeys
import `in`.hridayan.driftly.settings.data.local.SettingsProvider
import `in`.hridayan.driftly.settings.data.local.datastore.SettingsDataStore
import `in`.hridayan.driftly.settings.data.local.model.PreferenceGroup
import `in`.hridayan.driftly.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) : SettingsRepository {

    override fun getBoolean(key: SettingsKeys): Flow<Boolean> = dataStore.booleanFlow(key)
    override suspend fun setBoolean(key: SettingsKeys, value: Boolean) =
        dataStore.setBoolean(key, value)

    override suspend fun toggleSetting(key: SettingsKeys) = dataStore.toggle(key)

    override fun getInt(key: SettingsKeys): Flow<Int> = dataStore.intFlow(key)
    override suspend fun setInt(key: SettingsKeys, value: Int) = dataStore.setInt(key, value)

    override fun getFloat(key: SettingsKeys): Flow<Float> = dataStore.floatFlow(key)
    override suspend fun setFloat(key: SettingsKeys, value: Float) = dataStore.setFloat(key, value)

    override suspend fun getLookAndFeelPageList(): List<PreferenceGroup>{
        return SettingsProvider.lookAndFeelPageList
    }

    override suspend fun getDarkThemePageList(): List<PreferenceGroup> {
        return SettingsProvider.darkThemePageList
    }

    override suspend fun getAboutPageList(): List<PreferenceGroup> {
        return SettingsProvider.aboutPageList
    }

    override suspend fun getAutoUpdatePageList(): List<PreferenceGroup> {
        return SettingsProvider.autoUpdatePageList
    }

    override suspend fun getBehaviorPageList(): List<PreferenceGroup> {
        return SettingsProvider.behaviorPageList
    }

    override suspend fun getSettingsPageList(): List<PreferenceGroup> {
        return SettingsProvider.settingsPageList
    }

    override suspend fun getBackupPageList(): List<PreferenceGroup> {
        return SettingsProvider.backupPageList
    }
}